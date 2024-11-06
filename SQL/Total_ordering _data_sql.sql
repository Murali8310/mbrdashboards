---------------------------- General Queries--------------------------------------

use DB_MBR;
select * from MBRUsers where Desig_Id=7;
SELECT * FROM MBROrders ;
select * from MBRDesignation;
select * from MBRBrand;



---------------------------- Masters Query--------------------------------------
select distinct (Region) from MBROrders;
select distinct (Brand) from MBROrders;
select distinct(RetailerName)from MBROrders;
select distinct(RSName)from MBROrders;
SELECT DISTINCT RSCode, RSName FROM MBROrders;
select distinct(ABMEMM)from MBROrders;
select distinct(ABMKAM)from MBROrders;
select distinct(MBROrders.ABMKAM)from MBROrders join MBRUsers on MBRUsers.EmpId=MBROrders.ABMEMM;
select distinct(RBM)from MBROrders;


-- select orderQty, TotalPrice, count(RetailerCode)  from MBROrders distinct Month(OrderDate);


------------------------------- Monthly Trend -------------------------------

CREATE PROCEDURE GetOrderSummary
    @RegionList VARCHAR(MAX),     -- Comma-separated list of regions
    @StartDate INT,               -- Start date in yyyymmdd format (e.g., 20240601)
    @EndDate INT,                 -- End date in yyyymmdd format (e.g., 20240630)
    @RSNameList VARCHAR(MAX),
    @BrandList VARCHAR(MAX)
AS
BEGIN
    -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
    SELECT 
        -- Extract Year and Month to group by month
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
    GROUP BY
        YEAR(OrderDate),
        MONTH(OrderDate)
    ORDER BY
        YEAR(OrderDate),
        MONTH(OrderDate);  -- Order by year and month
END;

EXEC GetOrderSummary
    @RegionList = 'EAST',    -- Comma-separated list of regions
    @StartDate = 20240501,         -- Start date in yyyymmdd format
    @EndDate = 20240630,		   -- End date in yyyymmdd format
	@BrandList ='',
	@RSNameList ='';


----------------------------------------------------------------------Region Wise Monthly Distribution------------------------------------------------------




