
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

