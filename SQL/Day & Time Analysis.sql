
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


----------------------------------------------------------------------------------------  % of Orders by Weekday/Weekend &&  % of Orders by Weekday/Weekend Monthly ----------------------------------------------------------------------------------------------------------------------

Alter PROCEDURE PercentageofOrdersbyWeekdayorWeekend
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

    -- Select the percentage of orders for Weekdays and Weekends by Month
    SELECT 
        MONTH(OrderDate) AS OrderMonth,  -- Extract Month from OrderDate
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
        MONTH(OrderDate),  -- Group by Month of the order
        CASE 
            WHEN DATENAME(WEEKDAY, OrderDate) IN ('Saturday', 'Sunday') THEN 'Weekend'
            ELSE 'Weekday'
        END  -- Group by Weekday or Weekend
    ORDER BY
        OrderMonth, DayType; -- Order by Month and then by Weekday/Weekend category
END;

    
EXEC PercentageofOrdersbyWeekdayorWeekend
		@RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240430,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




------------------------------------------------------------------------------------------------------------------------% of Orders by Weekday/Weekend Region wise for selected Month-----------------------------------------------------------------------------

ALTER PROCEDURE PercentageofOrdersByWeekdayorWeekendRegionWise
    @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
    @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
    @ABMName VARCHAR(MAX),       -- New parameter for ABMName
    @RetailerType VARCHAR(MAX)   -- RetailerType parameter to filter by retailer type
AS
BEGIN
    -- CTE to calculate total orders per region for the specified date range
    WITH RegionOrderCount AS (
        SELECT 
            Region,
            COUNT(DISTINCT OrderNo) AS TotalOrders
        FROM 
            MBROrders (NOLOCK)
        WHERE 
            OrderDate BETWEEN @StartDate AND @EndDate
            AND (
                Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))
                OR Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ','))
                OR RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ','))
                OR (
                    @ABMName IS NOT NULL 
                    AND @ABMName <> '' 
                    AND (
                        ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                        OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                    )
                )
                OR (
                    @RetailerType IS NOT NULL 
                    AND @RetailerType <> '' 
                    AND RetailerCode LIKE 
                        CASE 
                            WHEN @RetailerType = 'IDD' THEN '9%'  
                            WHEN @RetailerType = 'DD' THEN '1%'   
                            ELSE ''  
                        END
                )
            )
        GROUP BY Region
    )

    -- Main query to calculate percentage of orders for weekdays and weekends by region
    SELECT 
        o.Region,  -- Region
        MONTH(o.OrderDate) AS OrderMonth,  -- Extract Month from OrderDate
        CASE 
            WHEN DATENAME(WEEKDAY, o.OrderDate) IN ('Saturday', 'Sunday') THEN 'Weekend'
            ELSE 'Weekday'
        END AS DayType,  -- Categorize into Weekday or Weekend
        COUNT(DISTINCT o.OrderNo) AS DistinctOrderCount,  -- Count of distinct orders for the day type
        (COUNT(DISTINCT o.OrderNo) * 1.0 / r.TotalOrders) * 100 AS PercentageOfOrders  -- Percentage within region
    FROM 
        MBROrders (NOLOCK) o
    JOIN 
        RegionOrderCount r ON o.Region = r.Region  -- Join with RegionOrderCount to get total orders per region
    WHERE 
        o.OrderDate BETWEEN @StartDate AND @EndDate  -- Date range for the orders
        AND (
            o.Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))
            OR o.Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ','))
            OR o.RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ','))
            OR (
                @ABMName IS NOT NULL 
                AND @ABMName <> '' 
                AND (
                    o.ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                    OR o.ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                )
            )
            OR (
                @RetailerType IS NOT NULL 
                AND @RetailerType <> '' 
                AND o.RetailerCode LIKE 
                    CASE 
                        WHEN @RetailerType = 'IDD' THEN '9%'  
                        WHEN @RetailerType = 'DD' THEN '1%'   
                        ELSE ''  
                    END
            )
        )
    GROUP BY
        o.Region, 
        MONTH(o.OrderDate),  -- Group by Month of the order
        CASE 
            WHEN DATENAME(WEEKDAY, o.OrderDate) IN ('Saturday', 'Sunday') THEN 'Weekend'
            ELSE 'Weekday'
        END,  -- Group by Weekday or Weekend
        r.TotalOrders  -- Include the TotalOrders in GROUP BY
    ORDER BY
        o.Region, OrderMonth, DayType;  -- Order by Region, Month, and then by Weekday/Weekend category
END;



    
	EXEC PercentageofOrdersByWeekdayorWeekendRegionWise
		@RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240430,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';


--------------------------------------------------------------------------------------------------------------------% of Orders by Hour of the Day on week day weekend---------------------------------------------------------------------------------------
ALTER PROCEDURE PercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend
    @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
    @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
    @ABMName VARCHAR(MAX),       -- New parameter for ABMName
    @RetailerType VARCHAR(MAX)   -- RetailerType parameter to filter by retailer type
AS
BEGIN
    -- CTE to calculate total orders for the specified date range
    WITH TotalOrderCount AS (
        SELECT 
            COUNT(DISTINCT OrderNo) AS TotalOrders
        FROM 
            MBROrders (NOLOCK)
        WHERE 
            OrderDate BETWEEN @StartDate AND @EndDate
            AND (
                Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ',')) 
                OR Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ',')) 
                OR RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ',')) 
                OR (
                    @ABMName IS NOT NULL 
                    AND @ABMName <> '' 
                    AND (
                        ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                        OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                    )
                )
                OR (
                    @RetailerType IS NOT NULL 
                    AND @RetailerType <> '' 
                    AND RetailerCode LIKE 
                        CASE 
                            WHEN @RetailerType = 'IDD' THEN '9%'  
                            WHEN @RetailerType = 'DD' THEN '1%'   
                            ELSE ''  
                        END
                )
            )
    )

    -- Main query to calculate percentage of orders for each hour of the day on weekdays and weekends
    SELECT 
        MONTH(o.OrderDate) AS OrderMonth,  -- Extract Month from OrderDate
        CASE 
            WHEN DATENAME(WEEKDAY, o.OrderDate) IN ('Saturday', 'Sunday') THEN 'Weekend'
            ELSE 'Weekday'
        END AS DayType,  -- Categorize into Weekday or Weekend
        DATEPART(HOUR, o.OrderTime) AS OrderHour,  -- Extract Hour of the Order from OrderTime
        COUNT(DISTINCT o.OrderNo) AS DistinctOrderCount,  -- Count of distinct orders for the hour
        (COUNT(DISTINCT o.OrderNo) * 1.0 / (SELECT TotalOrders FROM TotalOrderCount)) * 100 AS PercentageOfOrders  -- Percentage within total orders
    FROM 
        MBROrders (NOLOCK) o
    WHERE 
        o.OrderDate BETWEEN @StartDate AND @EndDate  -- Date range for the orders
        AND (
            o.Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ',')) 
            OR o.Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ',')) 
            OR o.RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ',')) 
            OR (
                @ABMName IS NOT NULL 
                AND @ABMName <> '' 
                AND (
                    o.ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                    OR o.ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                )
            )
            OR (
                @RetailerType IS NOT NULL 
                AND @RetailerType <> '' 
                AND o.RetailerCode LIKE 
                    CASE 
                        WHEN @RetailerType = 'IDD' THEN '9%'  
                        WHEN @RetailerType = 'DD' THEN '1%'   
                        ELSE ''  
                    END
            )
        )
    GROUP BY
        MONTH(o.OrderDate),  -- Group by Month of the order
        CASE 
            WHEN DATENAME(WEEKDAY, o.OrderDate) IN ('Saturday', 'Sunday') THEN 'Weekend'
            ELSE 'Weekday'
        END,  -- Group by Weekday or Weekend
        DATEPART(HOUR, o.OrderTime)  -- Group by Hour of the day based on OrderTime
    ORDER BY
        OrderMonth, DayType, OrderHour;  -- Order by Month, Weekday/Weekend, and then Hour of the day
END;


EXEC PercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend
		@RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240430,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';
