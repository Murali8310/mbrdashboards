use DB_MBR; 

select * from MBROrders;
 select * from MBRUsers where UserName like '1425137'

 SELECT 
    YEAR(OrderDate) AS OrderYear,         -- Year of the order
    MONTH(OrderDate) AS OrderMonth,       -- Month of the order
    COUNT(DISTINCT OrderNo) AS DistinctOrderCount,  -- Count of distinct orders

	from
    MBROrders
WHERE 
    OrderDate BETWEEN 20240101 AND 20241231  -- Date range for 2024 (integer format)
    AND (
        Region IN ('EAST', 'WEST', 'NORTH', 'SOUTH 1', 'SOUTH 2')  -- Filter for specific regions
    )
	
GROUP BY 
    YEAR(OrderDate), 
    MONTH(OrderDate)   -- Group by both Year and Month to get monthly data
ORDER BY 
    OrderYear, 
    OrderMonth;    


-------------------------------------------------------------------------------------------------------------------
-----------------------Monthly ordaring Behaviour------------------------------

Alter PROCEDURE MonthlyOrdaringBehaviour
    @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
    @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
    @ABMName VARCHAR(MAX),       -- New parameter for ABMName
    @RetailerType VARCHAR(MAX)   -- RetailerType parameter to filter by retailer type
AS
BEGIN
    -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
    SELECT 
        YEAR(OrderDate) AS OrderYear,         -- Extract Year from OrderDate
        MONTH(OrderDate) AS OrderMonth,       -- Extract Month from OrderDate
        COUNT(DISTINCT OrderNo) AS DistinctOrderCount,  -- Count of distinct orders
        SUM(OrderQty) * 1.0 / COUNT(DISTINCT OrderNo) AS AvgQtyPerOrder,  -- Avg quantity per order
        SUM(TotalPrice) / COUNT(DISTINCT OrderNo) AS AvgValuePerOrder    -- Avg value per order
    FROM 
        MBROrders (NOLOCK)  -- Using NOLOCK for the read operation
    WHERE 
		OrderDate BETWEEN @StartDate AND @EndDate  -- Date range for 2024 (integer format)
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
        YEAR(OrderDate),  -- Group by Year
        MONTH(OrderDate)  -- Group by Month
    ORDER BY
        OrderYear,  -- Order by Year
        OrderMonth; -- Order by Month
END;

	EXEC MonthlyOrdaringBehaviour
		@RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20241030,		   -- End date in yyyymmdd format
		@BrandList ='',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';


-----------------------------------------------------------------------------RegionWiseMonthlyDistributionNoofOrders---------------------------

Alter PROCEDURE RegionWiseMonthlyDistributionNoofOrders

    @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
    @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
    @ABMName VARCHAR(MAX),       -- New parameter for ABMName
    @RetailerType VARCHAR(MAX)   -- RetailerType parameter to filter by retailer type
AS
BEGIN
    -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
    SELECT 
        YEAR(OrderDate) AS OrderYear,         -- Extract Year from OrderDate
        MONTH(OrderDate) AS OrderMonth,       -- Extract Month from OrderDate
        COUNT(DISTINCT OrderNo) AS DistinctOrderCount,
		Region,-- Count of distinct orders,
		SUM(OrderQty) /COUNT(DISTINCT OrderNo)
    FROM 
        MBROrders (NOLOCK)  -- Using NOLOCK for the read operation
    WHERE 
		OrderDate BETWEEN @StartDate AND @EndDate  -- Date range for 2024 (integer format)
        AND (
            -- Filter by Region from the provided RegionList
            Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))
			)
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
     
    GROUP BY
        YEAR(OrderDate),  -- Group by Year
        MONTH(OrderDate),
		Region-- Group by Month
    ORDER BY
        OrderYear,  -- Order by Year
        OrderMonth; -- Order by Month
END;

	EXEC RegionWiseMonthlyDistributionNoofOrders
    @RegionList = 'EAST',    -- Comma-separated list of regions
    @StartDate = 20240401,         -- Start date in yyyymmdd format
    @EndDate = 20241030,		   -- End date in yyyymmdd format
	@BrandList ='CLOCK',
	@ABMName ='',
	@RetailerType='',
	@RSNameList=''



----------------------------------------------------------------------------------------------------------------------------


ALter PROCEDURE RegionWiseMonthlyAvgPerOrder

    @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
    @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
    @ABMName VARCHAR(MAX),       -- New parameter for ABMName
    @RetailerType VARCHAR(MAX)   -- RetailerType parameter to filter by retailer type
AS
BEGIN
    -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
    SELECT 
        YEAR(OrderDate) AS OrderYear,         -- Extract Year from OrderDate
        MONTH(OrderDate) AS OrderMonth,       -- Extract Month from OrderDate
		SUM(OrderQty) / COUNT(DISTINCT OrderNo) AS AvgQTYPerOrder,  
		SUM(TotalPrice) / COUNT(DISTINCT OrderNo) AS AvgQTYPriceOrder,
		Region
    FROM 
        MBROrders (NOLOCK)  -- Using NOLOCK for the read operation
    WHERE 
		OrderDate BETWEEN @StartDate AND @EndDate  -- Date range for 2024 (integer format)
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
        YEAR(OrderDate),  -- Group by Year
        MONTH(OrderDate) -- Group by Month
    ORDER BY
        OrderYear,  -- Order by Year
        OrderMonth; -- Order by Month
END;
----------------------------



ALTER PROCEDURE RegionWiseMonthlyAvgPerOrder
    @RegionList VARCHAR(MAX),    -- Comma-separated list of regions
    @StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX),
    @ABMName VARCHAR(MAX),       -- New parameter for ABMName
    @RetailerType VARCHAR(MAX)   -- RetailerType parameter to filter by retailer type
AS
BEGIN
    -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
    SELECT 
        YEAR(OrderDate) AS OrderYear,         -- Extract Year from OrderDate
        MONTH(OrderDate) AS OrderMonth,       -- Extract Month from OrderDate
        SUM(OrderQty) / COUNT(DISTINCT OrderNo) AS AvgQTYPerOrder,  
        SUM(TotalPrice) / COUNT(DISTINCT OrderNo) AS AvgQTYPriceOrder,
		Region
        
    FROM 
        MBROrders (NOLOCK)  -- Using NOLOCK for the read operation
    WHERE 
    OrderDate BETWEEN @StartDate AND @EndDate  -- Date range for 2024 (integer format)
    AND (
        -- Filter by Region from the provided RegionList
        Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RegionList, ','))

        -- Filter by Brand from the provided BrandList (Only apply this filter if BrandList is not empty)
        AND (@BrandList IS NULL OR @BrandList = '' OR Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@BrandList, ',')))

        -- Filter by RSName from the provided RSNameList (Only apply this filter if RSNameList is not empty)
        AND (@RSNameList IS NULL OR @RSNameList = '' OR RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ',')))

        -- Filter by ABMName if provided, and check ABMEMM or ABMKAM
        AND (
            @ABMName IS NULL 
            OR @ABMName = ''
            OR (
                ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
                OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ','))
            )
        )

        -- Filter by RetailerType if provided (Only apply this filter if RetailerType is not empty)
        AND (
            @RetailerType IS NULL 
            OR @RetailerType = '' 
            OR RetailerCode LIKE 
                CASE 
                    WHEN @RetailerType = 'IDD' THEN '9%'  -- Matches RetailerCodes starting with '9'
                    WHEN @RetailerType = 'DD' THEN '1%'   -- Matches RetailerCodes starting with '1'
                    ELSE ''  -- No filter applied if RetailerType is empty or undefined
                END
        )
    )

    GROUP BY
        YEAR(OrderDate),  -- Group by Year
        MONTH(OrderDate),
		Region-- Group by Month
    ORDER BY
        OrderYear,  -- Order by Year
        OrderMonth; -- Order by Month
                      -- Filter for EAST region
END;
	EXEC RegionWiseMonthlyAvgPerOrder
		@RegionList ='EAST', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20241030,		   -- End date in yyyymmdd format
		@BrandList ='CLOCK',
		@RSNameList ='',
		@ABMName='',
		@RetailerType='';








