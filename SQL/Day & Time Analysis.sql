
------------------general Queries--------
use DB_MBR;

select * from MBROrders;

select COUNT(distinct OrderNo) ,DAY(OrderDate) from MBROrders  where Month(OrderDate)=4  group by OrderDate order by DAY(OrderDate);


SELECT SUM(DailyOrderCount) AS TotalDistinctOrders
FROM (
    SELECT COUNT(DISTINCT OrderNo) AS DailyOrderCount
    FROM MBROrders
    WHERE MONTH(OrderDate) = 4
    GROUP BY DAY(OrderDate)
) AS DailyCounts ;



SELECT OrderDate,
       CASE 
           WHEN DATEPART(WEEKDAY, OrderDate) IN (1, 7) THEN 'Weekend'
           ELSE 'Weekday'
       END AS DayType
FROM MBROrders
Group by OrderDate
ORDER BY OrderDate; 


WITH DateRange AS (
    SELECT CAST(20240101 AS INT) AS date_int
    UNION ALL
    SELECT date_int + 1
    FROM DateRange
    WHERE date_int < 20240401
)
SELECT 
    date_int,
    DATENAME(WEEKDAY, CAST(CAST(date_int AS VARCHAR(8)) AS DATE)) AS day_name
FROM DateRange
OPTION (MAXRECURSION 30);  -- Increase the recursion limit

------------------------------------------------------- PercentageofOrdersbyDayoftheMonth -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE PROCEDURE PercentageofOrdersbyDayoftheMonth
    @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
    @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
    @ABMName VARCHAR(MAX),       -- New parameter for ABMName
    @RetailerType VARCHAR(MAX)   -- RetailerType parameter to filter by retailer type
AS
BEGIN
    -- Calculate total number of distinct orders for the specified date range
    DECLARE @TotalOrderCount INT;
    SELECT @TotalOrderCount = COUNT(DISTINCT OrderNo)
    FROM MBROrders (NOLOCK)
    WHERE 
        OrderDate BETWEEN @StartDate AND @EndDate
        AND (
            -- Filter by Region from the provided RegionList
            Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))
            -- Filter by Brand from the provided BrandList
            OR Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ','))
            -- Filter by RSName from the provided RSNameList
            OR RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ','))
            -- Filter by ABMName if provided, and check ABMEMM or ABMKAM
            OR (
                @ABMName IS NOT NULL 
                AND @ABMName <> '' 
                AND (
                    ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                    OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                )
            )
            -- Filter by RetailerType if provided
            OR (
                @RetailerType IS NOT NULL 
                AND @RetailerType <> '' 
                AND RetailerCode LIKE 
                    CASE 
                        WHEN @RetailerType = 'IDD' THEN '9%'  -- Matches RetailerCodes starting with '9'
                        WHEN @RetailerType = 'DD' THEN '1%'   -- Matches RetailerCodes starting with '1'
                        ELSE ''  -- No filter applied if RetailerType is empty or undefined
                    END
            )
        );

    -- Select the percentage of orders for each day of the month
    SELECT 
        DAY(OrderDate) AS OrderDay,                  -- Extract Day from OrderDate
        COUNT(DISTINCT OrderNo) AS DistinctOrderCount, -- Count of distinct orders for that day
        (COUNT(DISTINCT OrderNo) * 1.0 / @TotalOrderCount) * 100 AS PercentageOfOrders -- Percentage of orders for the day
    FROM 
        MBROrders (NOLOCK)  -- Using NOLOCK for the read operation
    WHERE 
        OrderDate BETWEEN @StartDate AND @EndDate  -- Date range for the orders
        AND (
            -- Filter by Region from the provided RegionList
            Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))
            -- Filter by Brand from the provided BrandList
            OR Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ','))
            -- Filter by RSName from the provided RSNameList
            OR RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ','))
            -- Filter by ABMName if provided, and check ABMEMM or ABMKAM
            OR (
                @ABMName IS NOT NULL 
                AND @ABMName <> '' 
                AND (
                    ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                    OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                )
            )
            -- Filter by RetailerType if provided
            OR (
                @RetailerType IS NOT NULL 
                AND @RetailerType <> '' 
                AND RetailerCode LIKE 
                    CASE 
                        WHEN @RetailerType = 'IDD' THEN '9%'  -- Matches RetailerCodes starting with '9'
                        WHEN @RetailerType = 'DD' THEN '1%'   -- Matches RetailerCodes starting with '1'
                        ELSE ''  -- No filter applied if RetailerType is empty or undefined
                    END
            )
        )
    GROUP BY
        DAY(OrderDate)  -- Group by Day of the month
    ORDER BY
        OrderDay; -- Order by Day of the month
END;
	EXEC PercentageofOrdersbyDayoftheMonth
		@RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240430,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';


	EXEC PercentageofOrdersbyDayoftheMonth
		@RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240501,         -- Start date in yyyymmdd format
		@EndDate = 20240530,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';


----------------------------------------------------------------------------------------% of Orders by Weekday/Weekend -----------------------------------------------------------------------------------------------------------------
CREATE PROCEDURE PercentageofOrdersbyWeekdayorWeekend
    @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
    @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
    @ABMName VARCHAR(MAX),       -- New parameter for ABMName
    @RetailerType VARCHAR(MAX)   -- RetailerType parameter to filter by retailer type
AS
BEGIN
    -- Calculate total number of distinct orders for the specified date range
    DECLARE @TotalOrderCount INT;
    SELECT @TotalOrderCount = COUNT(DISTINCT OrderNo)
    FROM MBROrders (NOLOCK)
    WHERE 
        OrderDate BETWEEN @StartDate AND @EndDate
        AND (
            -- Filter by Region from the provided RegionList
            Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))
            -- Filter by Brand from the provided BrandList
            OR Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ','))
            -- Filter by RSName from the provided RSNameList
            OR RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ','))
            -- Filter by ABMName if provided, and check ABMEMM or ABMKAM
            OR (
                @ABMName IS NOT NULL 
                AND @ABMName <> '' 
                AND (
                    ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                    OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                )
            )
            -- Filter by RetailerType if provided
            OR (
                @RetailerType IS NOT NULL 
                AND @RetailerType <> '' 
                AND RetailerCode LIKE 
                    CASE 
                        WHEN @RetailerType = 'IDD' THEN '9%'  -- Matches RetailerCodes starting with '9'
                        WHEN @RetailerType = 'DD' THEN '1%'   -- Matches RetailerCodes starting with '1'
                        ELSE ''  -- No filter applied if RetailerType is empty or undefined
                    END
            )
        );

    -- Select the percentage of orders for Weekdays and Weekends
    SELECT 
        CASE 
            WHEN DATENAME(WEEKDAY, OrderDate) IN ('Saturday', 'Sunday') THEN 'Weekend'
            ELSE 'Weekday'
        END AS DayType,  -- Categorize into Weekday or Weekend
        COUNT(DISTINCT OrderNo) AS DistinctOrderCount, -- Count of distinct orders for the day type
        (COUNT(DISTINCT OrderNo) * 1.0 / @TotalOrderCount) * 100 AS PercentageOfOrders -- Percentage of orders for the day type
    FROM 
        MBROrders (NOLOCK)  -- Using NOLOCK for the read operation
    WHERE 
        OrderDate BETWEEN @StartDate AND @EndDate  -- Date range for the orders
        AND (
            -- Filter by Region from the provided RegionList
            Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))
            -- Filter by Brand from the provided BrandList
            OR Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ','))
            -- Filter by RSName from the provided RSNameList
            OR RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ','))
            -- Filter by ABMName if provided, and check ABMEMM or ABMKAM
            OR (
                @ABMName IS NOT NULL 
                AND @ABMName <> '' 
                AND (
                    ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                    OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                )
            )
            -- Filter by RetailerType if provided
            OR (
                @RetailerType IS NOT NULL 
                AND @RetailerType <> '' 
                AND RetailerCode LIKE 
                    CASE 
                        WHEN @RetailerType = 'IDD' THEN '9%'  -- Matches RetailerCodes starting with '9'
                        WHEN @RetailerType = 'DD' THEN '1%'   -- Matches RetailerCodes starting with '1'
                        ELSE ''  -- No filter applied if RetailerType is empty or undefined
                    END
            )
        )
    GROUP BY
        CASE 
            WHEN DATENAME(WEEKDAY, OrderDate) IN ('Saturday', 'Sunday') THEN 'Weekend'
            ELSE 'Weekday'
        END  -- Group by Weekday or Weekend
    ORDER BY
        DayType; -- Order by Weekday/Weekend category
END;

	EXEC PercentageofOrdersbyWeekdayorWeekend
		@RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240430,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';


    


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------