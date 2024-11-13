---------------------------- General Queries--------------------------------------

use DB_MBR; 
select * from MBRUsers where Desig_Id=2 or Desig_Id=6;
SELECT * FROM MBROrders order by OrderDate;
select * from MBRDesignation;
select * from MBRBrand;
select * from ER_User_Master;
select * from MBRUsers where Desig_Id=4 ;
select * from MBRUsers where Desig_Id=5 ;
select COUNT(distinct ABMEMM) from MBROrders;
select COUNT(distinct ABMEMM) from MBROrders;
select COUNT(distinct ABMKAM) from MBROrders;

select *,Name,ABMEMM, ABMKAM from MBRUsers where Region='SOUTH 1' and (desig_id= 6 or desig_id=7)
EXEC sp_help 'MBRUsers';

SELECT ABMEMM, ABMKAM, COUNT(*) AS TotalOrders, SUM(OrderQty) AS TotalAmount
FROM MBROrders
WHERE ABMEMM = '1776314' OR ABMKAM = '1776314'
GROUP BY ABMEMM, ABMKAM;

select SUM(OrderQty) from MBROrders where ABMKAM = '1776314' GROUP BY ABMKAM;

select * from MBROrders where rs;
select * from MBRUsers where rs

---------------------------- Masters Query--------------------------------------
select distinct (Region) from MBROrders;
select distinct (Brand) from MBROrders;
select distinct(RetailerName)from MBROrders;
select distinct(RSName)from MBROrders;

--SELECT DISTINCT RSCode, RSName FROM MBROrders;
-- select distinct(ABMEMM)from MBROrders;
-- select distinct(ABMKAM)from MBROrders;
select * from MBRUsers where Desig_Id=7 or Desig_Id=6;

SELECT DISTINCT 
    MBROrders.RSCode, 
    MBROrders.RSName, 
    MBROrders.Region, 
    CASE
        WHEN ABMEMMUser.Name IS NOT NULL THEN ABMEMMUser.Name
        WHEN ABMKAMUser.Name IS NOT NULL THEN ABMKAMUser.Name
        ELSE 'No Name'  -- Optional, in case both are NULL
    END AS ABM_Name,  -- Changed alias to ABM_EMMName
    CASE
        WHEN ABMEMMUser.UserName IS NOT NULL THEN ABMEMMUser.UserName
        WHEN ABMKAMUser.UserName IS NOT NULL THEN ABMKAMUser.UserName
        ELSE 'No Name'  -- Optional, in case both are NULL
    END AS ABM_USERNAME  -- Changed alias to ABM_KAMName
FROM 
    MBROrders
LEFT JOIN 
    MBRUsers AS ABMEMMUser
    ON MBROrders.ABMEMM = ABMEMMUser.UserName
LEFT JOIN 
    MBRUsers AS ABMKAMUser
    ON MBROrders.ABMKAM = ABMKAMUser.UserName;


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
        MBROrders (nolock)
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
			MBROrders (nolock)
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
		@RegionList = 'east,west,north,south1,south2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20241030,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';

------------------------ Monthly Trend new Query optimized one --not using this--

ALTER PROCEDURE GetOrderSummary
  @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
  @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
  @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
  @RSNameList VARCHAR(MAX),
  @BrandList VARCHAR(MAX),
  @ABMName VARCHAR(MAX),       -- New parameter for ABMName
  @RetailerType VARCHAR(MAX)
AS
BEGIN
  -- Create temporary tables or use table variables to hold the split lists
  DECLARE @RegionTable TABLE (Region VARCHAR(100));
  DECLARE @BrandTable TABLE (Brand VARCHAR(100));
  DECLARE @RSNameTable TABLE (RSName VARCHAR(100));
  DECLARE @ABMTable TABLE (ABMName VARCHAR(100));

  -- Populate the temporary tables using STRING_SPLIT
  INSERT INTO @RegionTable (Region)
  SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ',');

  INSERT INTO @BrandTable (Brand)
  SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ',');

  INSERT INTO @RSNameTable (RSName)
  SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ',');

  IF @ABMName IS NOT NULL AND @ABMName <> ''
  BEGIN
    INSERT INTO @ABMTable (ABMName)
    SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',');
  END

  -- Main query for getting the order summary
  SELECT 
    YEAR(OrderDate) AS Year,
    MONTH(OrderDate) AS Month,
    SUM(TotalPrice) AS TotalPriceSum,
    SUM(OrderQty) AS TotalOrderQty,
    COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount
  FROM 
    MBROrders  with (NOLOCK)
  WHERE 
    OrderDate BETWEEN CAST(CAST(@StartDate AS CHAR(8)) AS varchar) AND CAST(CAST(@EndDate AS CHAR(8)) AS varchar)  -- Date comparison using BETWEEN

    -- Filtering based on Region, Brand, RSName, ABMName, and RetailerType using JOINs or EXISTS
    AND (
      EXISTS (SELECT 1 FROM @RegionTable r WHERE r.Region = MBROrders.Region)
      OR EXISTS (SELECT 1 FROM @BrandTable b WHERE b.Brand = MBROrders.Brand)
      OR EXISTS (SELECT 1 FROM @RSNameTable s WHERE s.RSName = MBROrders.RSName)
      OR (
        @ABMName IS NOT NULL AND @ABMName <> '' AND (
          EXISTS (SELECT 1 FROM @ABMTable a WHERE a.ABMName = MBROrders.ABMEMM)
          OR EXISTS (SELECT 1 FROM @ABMTable a WHERE a.ABMName = MBROrders.ABMKAM)
        )
      )
      OR (
        @RetailerType IS NOT NULL AND (
          -- Logic to filter RetailerType
          (@RetailerType = 'IDD' AND MBROrders.RetailerCode LIKE '9%')
          OR (@RetailerType = 'DD' AND MBROrders.RetailerCode LIKE '1%')
          OR (@RetailerType NOT IN ('IDD', 'DD')) -- This allows for cases where RetailerType is not IDD or DD
        )
      )
    )
  GROUP BY 
    YEAR(OrderDate),
    MONTH(OrderDate)
  ORDER BY 
    YEAR(OrderDate),
    MONTH(OrderDate);
END;



EXEC GetOrderSummary
		@RegionList = 'EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20241030,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';



		EXEC GetOrderSummary @RegionList = 'EAST, WEST, NORTH, SOUTH 1, SOUTH 2', @StartDate = '20240401', @EndDate = '20241001', @BrandList = '', @RSNameList = '', @ABMName = '', @RetailerType = '';


EXEC GetOrderSummary
		@RegionList = '', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20241030,		   -- End date in yyyymmdd format
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
            MBROrders(nolock)
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
    @StartDate = 20240401,         -- Start date in yyyymmdd format
    @EndDate = 20241030,		   -- End date in yyyymmdd format
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
	@ABMName VARCHAR(MAX),
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
        COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount,
		Region
    FROM 
        MBROrders(nolock)
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
	@ABMName='',
	@RetailerType=''

----------------------------------------------------------------------------------Region Wise Growth over Previous Months-------------------------------------------------------

Alter PROCEDURE RegionWiseGrowthoverPreviousMonths
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
            Region,
            SUM(TotalPrice) AS TotalPriceSum,
            SUM(OrderQty) AS TotalOrderQty,
            COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount
        FROM 
            MBROrders(nolock)
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
        END AS RetailerGrowthPercentage,

				  -- Calculate the growth in TotalPrice (month-over-month growth)
        CASE 
            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                (TotalPriceSum - LAG(TotalPriceSum, 1) OVER ( PARTITION BY Region ORDER BY Year, Month))
        END AS PriceGrowth,

		 -- Calculate the growth in TotalOrderQty (month-over-month growth)
        CASE 
            WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                (TotalOrderQty - LAG(TotalOrderQty, 1) OVER ( PARTITION BY Region ORDER BY Year, Month))
        END AS OrderGrowth, 

		CASE 
            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL
            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record
            ELSE 
                (DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER ( PARTITION BY Region ORDER BY Year, Month))
        END AS RetailerGrowth

    FROM 
        MonthlySummary
    ORDER BY
        Region, Year, Month;
END;


EXEC RegionWiseGrowthoverPreviousMonths
    @RegionList = 'EAST, WEST, NORTH, SOUTH 1, SOUTH 2',    -- Comma-separated list of regions
    @StartDate = 20240401,         -- Start date in yyyymmdd format
    @EndDate = 20241030,		   -- End date in yyyymmdd format
	@BrandList ='',
	@ABMName ='',
	@RetailerType='',
	@RSNameList=''

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


