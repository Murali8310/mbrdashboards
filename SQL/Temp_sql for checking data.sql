use DB_MBR; 
ALTER PROCEDURE GetOrderSummary_temp
  @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
  @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
  @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
  @RSNameList VARCHAR(MAX),
  @BrandList VARCHAR(MAX),
  @ABMName VARCHAR(MAX),
  @RetailerType VARCHAR(MAX)   -- New parameter for ABMName
AS
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
	SELECT * from  @RegionTable
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
        O.OrderDate >= CONVERT(DATE, CAST(@StartDate AS CHAR(8)))   -- Convert to DATE format for comparison
        AND O.OrderDate <= CONVERT(DATE, CAST(@EndDate AS CHAR(8))) 
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

		EXEC GetOrderSummary_temp
		@RegionList = 'EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240630,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='IDD';

		select * from MBROrders where RetailerCode not like '9%'
		select * from MBRUsers;
		select * from MBRORDERS;


ALTER PROCEDURE GrowthOverPreviousMonth_temp
    @RegionList VARCHAR(MAX),     -- Comma-separated list of regions
    @StartDate INT,               -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                 -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
	@ABMName VARCHAR(MAX),
	@RetailerType VARCHAR(MAX)
AS
BEGIN
    ;WITH MonthlySummary AS (
        -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode per month
        SELECT 
            YEAR(OrderDate) AS Year,
            MONTH(OrderDate) AS Month,
            SUM(TotalPrice) AS TotalPriceSum,
            SUM(OrderQty) AS TotalOrderQty,
            COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount
        FROM 
            MBROrders
        WHERE 
            (
                Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))  -- Split the @RegionList string into values
                AND CONVERT(VARCHAR, OrderDate, 112) >= @StartDate                        -- Compare OrderDate with StartDate
                AND CONVERT(VARCHAR, OrderDate, 112) <= @EndDate                          -- Compare OrderDate with EndDate
            )
            OR 
            Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ','))  -- Split the @BrandList string into values
            OR 
            RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ',')) -- Split the @RSNameList string into values
			OR
			(
				-- Check if ABMName is provided and exists in ABMEMM or ABMKAM columns
				@ABMName IS NOT NULL AND @ABMName <> '' AND (
					ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
					OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ','))
				) 
			)
			OR
			(
			@RetailerType IS NOT NULL AND(
				 RetailerCode LIKE 
				 CASE 
				  WHEN @RetailerType = 'IDD' THEN '9%'
			 WHEN @RetailerType = 'DD' THEN '1%'
			 ELSE ''
			 END
		)
		)
        GROUP BY
            YEAR(OrderDate),
            MONTH(OrderDate)
    )
    -- Now, calculate the growth over the previous month using the LAG function and compute percentage growth
    SELECT 
        Year,
        Month,
        TotalPriceSum,
        TotalOrderQty,
        DistinctRetailerCount,
        


		  -- Calculate the percentage growth in TotalPrice (month-over-month growth)
        CASE 
            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                (TotalPriceSum - LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month)) * 1.0 
        END AS PriceGrowth,
        
        -- Calculate the percentage growth in TotalPrice (month-over-month growth)
        CASE 
            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                ((TotalPriceSum - LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month)) * 1.0 / LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month)) * 100
        END AS PriceGrowthPercentage,
        
        -- Calculate the percentage growth in TotalOrderQty (month-over-month growth)
        CASE 
            WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                ((TotalOrderQty - LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month)) * 1.0 / LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month)) * 100
        END AS OrderQtyGrowthPercentage,
        
        -- Calculate the percentage growth in DistinctRetailerCount (month-over-month growth)
        CASE 
            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                ((DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month)) * 1.0 / LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month)) * 100
        END AS RetailerGrowthPercentage

		--------------Added


    FROM 
        MonthlySummary
    ORDER BY
        Year,
        Month;
END;


EXEC GrowthOverPreviousMonth
    @RegionList =     -- Comma-separated list of regions
    @StartDate = 20230201,         -- Start date in yyyymmdd format
    @EndDate = 20240630,		   -- End date in yyyymmdd format
	@BrandList ='',
	@RSNameList ='',
	@ABMName='',
	@RetailerType ='IDD'
