use DB_MBR; 
----------------- MOnthly trend------------
ALTER PROCEDURE GetOrderSummary_temp
  @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
  @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
  @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
  @RSNameList VARCHAR(MAX),
  @BrandList VARCHAR(MAX),
  @ABMName VARCHAR(MAX),
  @RetailerType VARCHAR(MAX) 
BEGIN
    -- Declare table variables to store the split values
    DECLARE @RegionTable TABLE (Region VARCHAR(255));
    DECLARE @BrandTable TABLE (Brand VARCHAR(255));
    DECLARE @RSNameTable TABLE (RSName VARCHAR(255));
    DECLARE @ABMNameTable TABLE (ABMName VARCHAR(255));

    -- Insert the split values into the table variables
    INSERT INTO @RegionTable (Region)
    SELECT LTRIM(RTRIM(value)) 
    FROM STRING_SPLIT(@RegionList, ',');

    INSERT INTO @BrandTable (Brand)
    SELECT LTRIM(RTRIM(value)) 
    FROM STRING_SPLIT(@BrandList, ',');

    INSERT INTO @RSNameTable (RSName)
    SELECT LTRIM(RTRIM(value)) 
    FROM STRING_SPLIT(@RSNameList, ',');

    INSERT INTO @ABMNameTable (ABMName)
    SELECT LTRIM(RTRIM(value)) 
    FROM STRING_SPLIT(@ABMName, ',');

    -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
    SELECT 
        YEAR(OrderDate) AS Year,
        MONTH(OrderDate) AS Month,
        SUM(TotalPrice) AS TotalPriceSum,
        SUM(OrderQty) AS TotalOrderQty,
        COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount
    FROM 
        MBROrders O
    LEFT JOIN 
        @RegionTable R ON O.Region = R.Region
    LEFT JOIN 
        @BrandTable B ON O.Brand = B.Brand
    LEFT JOIN 
        @RSNameTable RS ON O.RSName = RS.RSName
    LEFT JOIN 
        @ABMNameTable ABM ON O.ABMEMM = ABM.ABMName OR O.ABMKAM = ABM.ABMName
    WHERE 

				CONVERT(VARCHAR, OrderDate, 112) >= @StartDate                        -- Compare OrderDate with StartDate
				AND CONVERT(VARCHAR, OrderDate, 112) <= @EndDate  
		OR
		R.Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))  -- Split the @RegionList string into values
		OR 
			B.Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ','))  -- Split the @BrandList string into values
		OR 
			RS.RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ',')) -- Split the @RSNameList string into values
        OR (
            @RetailerType IS NOT NULL AND 
            (O.RetailerCode LIKE 
                CASE 
                    WHEN @RetailerType = 'IDD' THEN '9%' 
                    WHEN @RetailerType = 'DD' THEN '1%' 
                    ELSE '' 
                END
            )
        )
		OR
			(
				ABM.ABMName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) -- Split the @RSNameList string into values
			)
    GROUP BY
        YEAR(OrderDate),
        MONTH(OrderDate)
    ORDER BY
        YEAR(OrderDate),
        MONTH(OrderDate);  -- Order by year and month
END;


EXEC GetOrderSummary_temp
		@RegionList = 'EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240630,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';



---------------------------------------------------

	EXEC GetOrderSummary
		@RegionList = 'EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240630,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';
