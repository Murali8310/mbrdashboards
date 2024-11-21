
	select TOP 5
	 ProductCode,                                 -- SKU column
    SUM(OrderQty) AS TotalOrderQty from MBROrders  
	group by ProductCode
	order by TotalOrderQty desc

----------------------------------------------------------------------------Overall------------------------------
Alter PROCEDURE OverallSKUOrdered
		@RegionList VARCHAR(MAX),    -- Comma-separated list of regions
		@StartDate INT,              -- Start date in yyyymmdd format (e.g., 20240601)
		@EndDate INT,                -- End date in yyyymmdd format (e.g., 20240630)
	--	@RSNameList VARCHAR(MAX),
		@BrandList VARCHAR(MAX),
		--@ABMName VARCHAR(MAX),       -- New parameter for ABMName
		@RetailerType VARCHAR(MAX) 
	AS
	BEGIN
		-- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode
		SELECT 
			-- Extract Year and Month to group by month
			TOP 5
	 ProductCode,                                 -- SKU column
    SUM(OrderQty) AS TotalOrderQty
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
		--	OR 
			--RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@RSNameList, ',')) -- Split the @RSNameList string into values
		--	OR
	--		(
				-- Check if ABMName is provided and exists in ABMEMM or ABMKAM columns
		--		@ABMName IS NOT NULL AND @ABMName <> '' AND (
	--				ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ',')) 
	--	--			OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT(@ABMName, ','))
	--			) 
	--		)
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
			ProductCode

		ORDER BY
			TotalOrderQty desc
			-- Order by year and month
	END;

	EXEC OverallSKUOrdered
		@RegionList ='EAST, WEST, NORTH, SOUTH 1, SOUTH 2', -- Comma-separated list of regions
		@StartDate = 20240401,         -- Start date in yyyymmdd format
		@EndDate = 20240430,		   -- End date in yyyymmdd format
		@BrandList ='',
	--	@RSNameList ='',
	--	@ABMName='',
		@RetailerType='';
-----------------------------------------------------------------------------Region Selected-------------------------
----------------------------------------------------------------RS selected---------------------------------------------

