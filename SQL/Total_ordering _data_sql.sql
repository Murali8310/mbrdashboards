---------------------------- General Queries--------------------------------------

use DB_MBR; 
select * from MBRUsers where Desig_Id=7 or Desig_Id=6;
SELECT * FROM MBROrders order by OrderDate;
select * from MBRDesignation;
select * from MBRBrand;
select COUNT(*) from MBRUsers where Desig_Id=7 ;
select COUNT(*) from MBRUsers where Desig_Id=6 ;
select COUNT(distinct ABMEMM) from MBROrders;
select COUNT(distinct ABMEMM) from MBROrders;
select COUNT(distinct ABMKAM) from MBROrders;
EXEC sp_help 'MBROrders';

SELECT ABMEMM, ABMKAM, COUNT(*) AS TotalOrders, SUM(OrderQty) AS TotalAmount
FROM MBROrders
WHERE ABMEMM = '1776314' OR ABMKAM = '1776314'
GROUP BY ABMEMM, ABMKAM;

select SUM(OrderQty) from MBROrders where ABMKAM = '1776314' GROUP BY ABMKAM;

---------------------------- Masters Query--------------------------------------
select distinct (Region) from MBROrders;
select distinct (Brand) from MBROrders;
select distinct(RetailerName)from MBROrders;
select distinct(RSName)from MBROrders;
SELECT DISTINCT RSCode, RSName FROM MBROrders;
-- select distinct(ABMEMM)from MBROrders;
-- select distinct(ABMKAM)from MBROrders;
select * from MBRUsers where Desig_Id=7 or Desig_Id=6;


-- select orderQty, TotalPrice, count(RetailerCode)  from MBROrders distinct Month(OrderDate);

-------------------------------------------Landing Page Queries-------------------------------------------------------------------------

------------------tiles------------------------------------
   SELECT 
        -- Extract Year and Month to group by month
        YEAR(OrderDate) AS Year,
        MONTH(OrderDate) AS Month,
        SUM(TotalPrice) AS OrderValue,
        SUM(OrderQty) AS TotalOrder,
		COUNT(DISTINCT OrderNo) AS OrderQty,
        COUNT(DISTINCT RetailerCode) AS delalers,
		Region
    FROM 
        MBROrders
	Where 
			CONVERT(VARCHAR, OrderDate, 112) >= 20240401                        -- Compare OrderDate with StartDate
            AND CONVERT(VARCHAR, OrderDate, 112) <= 20240530
	Group BY
	 YEAR(OrderDate),
        MONTH(OrderDate),
		Region
    ORDER BY
        YEAR(OrderDate),
        MONTH(OrderDate); 	
		

------------------------charts---------------------------------------
	SELECT 
        -- Extract Year and Month to group by month
        YEAR(OrderDate) AS Year,
        MONTH(OrderDate) AS Month,
        SUM(TotalPrice) AS OrderValue,
        SUM(OrderQty) AS TotalOrder
    FROM 
        MBROrders
	Where 
			CONVERT(VARCHAR, OrderDate, 112) >= 20240401                        -- Compare OrderDate with StartDate
            AND CONVERT(VARCHAR, OrderDate, 112) <= 20240530
	Group BY
	 YEAR(OrderDate),
        MONTH(OrderDate)
    ORDER BY
        YEAR(OrderDate),
        MONTH(OrderDate);


------------------------------------------------------------------------- Monthly Trend -------------------------------
--- Monthly Trend Old Backup
Alter PROCEDURE GetOrderSummary
  @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
		@StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
		@EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
		@RSNameList VARCHAR(MAX),
		@BrandList VARCHAR(MAX),
		@ABMName VARCHAR(MAX),       -- New parameter for ABMName
		@RetailerType VARCHAR(MAX) 
	AS
	BEGIN
		-- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
		SELECT 
			-- Extract Year and Month to group by month
			YEAR(OrderDate) AS Year,
			MONTH(OrderDate) AS Month,
			SUM(TotalPrice) AS TotalPriceSum,
			SUM(OrderQty) AS TotalOrderQty,
			COUNT( distinct RetailerCode) AS DistinctRetailerCount
		--	ABMEMM as EMPID ,
		
		FROM 
			MBROrders
		WHERE 
				 CONVERT(VARCHAR, OrderDate, 112) >= @StartDate                        -- Compare OrderDate with StartDate
				AND CONVERT(VARCHAR, OrderDate, 112) <= @EndDate                          
			AND (
				Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))  -- Split the @RegionList string into values
				
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

		ORDER BY
			YEAR(OrderDate),
			MONTH(OrderDate);  -- Order by year and month
	END;


	EXEC GetOrderSummary
		@RegionList = '', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240630,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='IDD';

------------------------ Monthly Trend new Query optimized one ----

ALTER PROCEDURE GetOrderSummary
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
        O.OrderDate >= CONVERT(VARCHAR, CAST(@StartDate AS CHAR(8)))   -- Convert to DATE format for comparison
        AND O.OrderDate <= CONVERT(VARCHAR, CAST(@EndDate AS CHAR(8))) 

		 --CONVERT(VARCHAR, OrderDate, 112) >= @StartDate                        -- Compare OrderDate with StartDate
		--		AND CONVERT(VARCHAR, OrderDate, 112) <= @EndDate  
        AND (
            @RetailerType IS NULL OR 
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



EXEC GetOrderSummary
		@RegionList = '', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240630,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='IDD';

----------------------------------------------------------------------Growth over Previous Month------------------------------------------------------
 Alter PROCEDURE GrowthOverPreviousMonth
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
        
		 -- Calculate the percentage growth in DistinctRetailerCount (month-over-month growth)
        CASE 
            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                ((DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month)) * 1.0 / LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month)) * 100
        END AS RetailerGrowthPercentage,

		  -- Calculate the growth in TotalPrice (month-over-month growth)
        CASE 
            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                (TotalPriceSum - LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month))
        END AS PriceGrowth,

		 -- Calculate the growth in TotalOrderQty (month-over-month growth)
        CASE 
            WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                (TotalOrderQty - LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month))
        END AS OrderGrowth, 

		CASE 
            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                (DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month))
        END AS RetailerGrowth,

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
        END AS OrderQtyGrowthPercentage
    FROM 
        MonthlySummary
    ORDER BY
        Year,
        Month;
END;



EXEC GrowthOverPreviousMonth
   @RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2' ,  -- Comma-separated list of regions
    @StartDate = 20230201,         -- Start date in yyyymmdd format
    @EndDate = 20240630,		   -- End date in yyyymmdd format
	@BrandList ='',
	@RSNameList ='',
	@ABMName='',
	@RetailerType =''

----------------------------------------------------------------------------------Region Wise Monthly Distribution-------------------------------------------------------


Alter PROCEDURE RegionWiseMonthlyDistribution

    @RegionList VARCHAR(MAX),     -- Comma-separated list of regions
    @StartDate INT,               -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                 -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
	@ABMName VARCHAR(MAX)
AS
BEGIN
    -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
    SELECT 
        -- Extract Year and Month to group by month
        YEAR(OrderDate) AS Year,
        MONTH(OrderDate) AS Month,
        SUM(TotalPrice) AS TotalPriceSum,
        SUM(OrderQty) AS TotalOrderQty,
        COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount,
		Region
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
    GROUP BY
        YEAR(OrderDate),
        MONTH(OrderDate),
		Region
    ORDER BY
        YEAR(OrderDate),
        MONTH(OrderDate);  -- Order by year and month
END;

EXEC RegionWiseMonthlyDistribution
    @RegionList = 'EAST',    -- Comma-separated list of regions
    @StartDate = 20240501,         -- Start date in yyyymmdd format
    @EndDate = 20240630,		   -- End date in yyyymmdd format
	@BrandList ='',
	@RSNameList ='',
	@ABMName=''

----------------------------------------------------------------------------------Region Wise Growth over Previous Months-------------------------------------------------------

Alter PROCEDURE RegionWiseGrowthoverPreviousMonths
    @RegionList VARCHAR(MAX),     -- Comma-separated list of regions
    @StartDate INT,               -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                 -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
	@ABMName VARCHAR(MAX)
AS
BEGIN
    ;WITH MonthlySummary AS (
        -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode per month
        SELECT 
            YEAR(OrderDate) AS Year,
            MONTH(OrderDate) AS Month,
            Region,
            SUM(TotalPrice) AS TotalPriceSum,
            SUM(OrderQty) AS TotalOrderQty,
            COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount
        FROM 
            MBROrders
        WHERE 
            Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))  -- Split the @RegionList string into values
            AND CONVERT(VARCHAR, OrderDate, 112) >= @StartDate                        -- Compare OrderDate with StartDate
            AND CONVERT(VARCHAR, OrderDate, 112) <= @EndDate                          -- Compare OrderDate with EndDate
            OR (Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ',')) OR @BrandList IS NULL)
            OR (RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ',')) OR @RSNameList IS NULL)
			OR
			(
				-- Check if ABMName is provided and exists in ABMEMM or ABMKAM columns
				@ABMName IS NOT NULL AND @ABMName <> '' AND (
					ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
					OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ','))
				) 
			)
        GROUP BY
            YEAR(OrderDate),
            MONTH(OrderDate),
            Region
    )
    -- Now, calculate the growth over the previous month using the LAG function and compute percentage growth
    SELECT 
        Year,
        Month,
        Region,
        TotalPriceSum,
        TotalOrderQty,
        DistinctRetailerCount,
        
        -- Calculate the percentage growth in TotalPrice (month-over-month growth)
        CASE 
            WHEN LAG(TotalPriceSum, 1) OVER (PARTITION BY Region ORDER BY Year, Month) IS NULL THEN NULL
            WHEN LAG(TotalPriceSum, 1) OVER (PARTITION BY Region ORDER BY Year, Month) = 0 THEN NULL
            ELSE 
                ((TotalPriceSum - LAG(TotalPriceSum, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 1.0 / LAG(TotalPriceSum, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 100
        END AS PriceGrowthPercentage,
        
        -- Calculate the percentage growth in TotalOrderQty (month-over-month growth)
        CASE 
            WHEN LAG(TotalOrderQty, 1) OVER (PARTITION BY Region ORDER BY Year, Month) IS NULL THEN NULL
            WHEN LAG(TotalOrderQty, 1) OVER (PARTITION BY Region ORDER BY Year, Month) = 0 THEN NULL
            ELSE 
                ((TotalOrderQty - LAG(TotalOrderQty, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 1.0 / LAG(TotalOrderQty, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 100
        END AS OrderQtyGrowthPercentage,
        
        -- Calculate the percentage growth in DistinctRetailerCount (month-over-month growth)
        CASE 
            WHEN LAG(DistinctRetailerCount, 1) OVER (PARTITION BY Region ORDER BY Year, Month) IS NULL THEN NULL
            WHEN LAG(DistinctRetailerCount, 1) OVER (PARTITION BY Region ORDER BY Year, Month) = 0 THEN NULL
            ELSE 
                ((DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 1.0 / LAG(DistinctRetailerCount, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 100
        END AS RetailerGrowthPercentage
    FROM 
        MonthlySummary
    ORDER BY
        Region, Year, Month;
END;


EXEC RegionWiseGrowthoverPreviousMonths
    @RegionList = 'EAST',    -- Comma-separated list of regions
    @StartDate = 20240401,         -- Start date in yyyymmdd format
    @EndDate = 20240630,		   -- End date in yyyymmdd format
	@BrandList ='',
	@ABMName ='';


