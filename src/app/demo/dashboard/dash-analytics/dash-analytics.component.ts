// angular import
import { Component, ViewChild, NO_ERRORS_SCHEMA, AfterViewInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApexOptions } from 'ng-apexcharts'; // Use ApexOptions instead of ChartOptions
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NgxSpinnerModule } from 'ngx-spinner';
import { NgxSpinnerService } from 'ngx-spinner';

// project import
import { SharedModule } from 'src/app/theme/shared/shared.module';
import { ApexGrid, ApexMarkers, ApexTitleSubtitle, NgApexchartsModule } from 'ng-apexcharts';
import { ProductSaleComponent } from './product-sale/product-sale.component';
declare var $: any; // Declare jQuery globally
type DropdownFlag = 'isDropdownOpenForRetailer' | 'isDropdownOpenForRS' | 'isDropdownOpenForBrand' | 'isDropdownOpen' | 'isDropdownOpenForAbm' | 'isDropdownOpenForMonth';

interface Brand {
  id: number;
  name: string;
}
// import {
//   ChartComponent,
//   ApexAxisChartSeries,
//   ApexNonAxisChartSeries,
//   ApexChart,
//   ApexXAxis,
//   ApexDataLabels,
//   ApexStroke,
//   ApexYAxis,
//   ApexLegend,
//   ApexFill,
//   ApexGrid,
//   ApexPlotOptions,
//   ApexTooltip,
//   ApexMarkers
// } from 'ng-apexcharts';

import {
  ApexAxisChartSeries,
  ApexChart,
  ChartComponent,
  ApexDataLabels,
  ApexPlotOptions,
  ApexYAxis,
  ApexLegend,
  ApexStroke,
  ApexXAxis,
  ApexFill,
  ApexTooltip
} from 'ng-apexcharts';
import { DashboardService } from 'src/app/dashboard.service';
import { ActivatedRoute, Router, RouterStateSnapshot } from '@angular/router';

// export type ChartOptions = {
//   series: ApexAxisChartSeries | ApexNonAxisChartSeries;
//   chart: ApexChart;
//   xaxis: ApexXAxis;
//   stroke: ApexStroke;
//   dataLabels: ApexDataLabels;
//   plotOptions: ApexPlotOptions;
//   yaxis: ApexYAxis;
//   tooltip: ApexTooltip;
//   labels: string[];
//   colors: string[];
//   legend: ApexLegend;
//   fill: ApexFill;
//   grid: ApexGrid;
//   markers: ApexMarkers;
// };

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  yaxis: ApexYAxis;
  xaxis: ApexXAxis;
  fill: ApexFill;
  tooltip: ApexTooltip;
  stroke: ApexStroke;
  legend: ApexLegend;
  title: ApexTitleSubtitle;
  markers: ApexMarkers;
  grid: ApexGrid;
};

@Component({
  selector: 'app-dash-analytics',
  standalone: true,
  imports: [CommonModule, NgxSpinnerModule, SharedModule, NgApexchartsModule, ProductSaleComponent, NgMultiSelectDropDownModule],
  templateUrl: './dash-analytics.component.html',
  styleUrls: ['./dash-analytics.component.scss']
})
export default class DashAnalyticsComponent {
  chartOptionsline!: ApexOptions; // Change to ApexOptions
  chartOptionsRegionwise!: ApexOptions;
  chartOptionsRegionwiseGrowth!: ApexOptions;
  regionWiseGrowthOptions!:ApexOptions;
  budgetChartOptions!:ApexOptions;
  RegionWiseMonthlyDistibutionOptions!:ApexOptions;
  RegionWiseGrowthOverPreviousMonthOptions!:ApexOptions;



  // This is for menu id 3
  chartOptionslineForOrdBh!: ApexOptions; // Change to ApexOptions
  RegionWiseMonthlyDistibutionOptionsFOrdBh!: ApexOptions;
  RegionWiseMonthlyAvgPerOrder!: ApexOptions;

  public appendClass: any;


  // percenate of orders cnharts.

  percentageOfOrdersOfDayInMonthOptions!:ApexOptions;
  percentageofOrdersbyWeekdayorWeekendOptionsPiechart!:ApexOptions;
  percentageofOrdersbyWeekdayorWeekendOptionsBarchart!:ApexOptions;
  percentageofOrdersByWeekdayorWeekendRegionWiseOptions!:ApexOptions;
  percentageofOrdersByHourOfTheDayOptionsBarchart!:ApexOptions;

  @HostListener('window:click', ['$event'])
  onWindowClick(event: MouseEvent) {
    // Ignore clicks on the specific button with id 'allowClickButton'
    const targetElement = event.target as HTMLElement;

    if (
      targetElement.closest('#allowClickButton') || targetElement.closest('#allowClickButtonabm') || targetElement.closest('#restrictclicksabm')||
      targetElement.closest('#restrictclicks') || targetElement.closest('#retailerTypeallow') || targetElement.closest('#retailerTypeallow') || targetElement.closest('#retailerTypeallowDropdown')
      ||targetElement.closest('#rsnamemenu2')||targetElement.closest('#rsnamemenu') || targetElement.closest('#brandDropdown') ||targetElement.closest('#brandDropdownmenu') || targetElement.closest('#monthDropdownmenu') || targetElement.closest('#monthDropdown')
    ) {
      return; // Ignore clicks on this button and its children
    }

    // If dropdown is open and click is outside the filter, close it
    if (this.isDropdownOpen) {
      this.isDropdownOpen = !this.isDropdownOpen;
    }
    if (this.isDropdownOpenForAbm) {
      this.isDropdownOpenForAbm = !this.isDropdownOpenForAbm;
    }

    if (this.isDropdownOpenForRetailer) {
      this.isDropdownOpenForRetailer = !this.isDropdownOpenForRetailer;
    }
    if (this.isDropdownOpenForRS) {
      this.isDropdownOpenForRS = !this.isDropdownOpenForRS;
    }
    if (this.isDropdownOpenForBrand) {
      this.isDropdownOpenForBrand = !this.isDropdownOpenForBrand;
    }
    if(this.isDropdownOpenForMonth){
      this.isDropdownOpenForMonth = !this.isDropdownOpenForMonth;
    }

    
    
  }
  isOpen = false;
  selectedItems: string[] = [];

  items: string[] = ['New Delhi', 'Mumbai', 'Bangalore', 'Pune', 'Chennai', 'Navsari'];
  filteredItems: string[] = this.items;
  searchTerm: string = '';
  regions = [
    { id: 1, name: 'East' },
    { id: 2, name: 'North' },
    { id: 3, name: 'South1' },
    { id: 4, name: 'South2' },
    { id: 5, name: 'West' }
  ];

  availableRegions: any = [
    { id: 1, name: 'East' },
    { id: 2, name: 'North' },
    { id: 3, name: 'South1' },
    { id: 4, name: 'South2' },
    { id: 5, name: 'West' }
  ];
  selectedRegions: { id: number; name: string }[] = [];
  isDropdownOpen = false;
  searchInputValue = '';
  filteredRegionsList = this.availableRegions;

  availableAbmNames: any = [
    // { id: 1, name: 'ABM1' },
    // { id: 2, name: 'ABM2' },
    // { id: 3, name: 'ABM3' },
    // { id: 4, name: 'ABM4' },
    // { id: 5, name: 'ABM5' }
  ];

  // Holds selected ABM names
  selectedAbmNames: { id: number; name: string }[] = [];

  // Control dropdown visibility
  isDropdownOpenForAbm = false;

  // Search input value
  searchInputValueForAbm = '';

  // Filtered ABM names based on search input
  filteredAbmNamesList = this.availableAbmNames;

  abmNames = [
    { id: 1, name: 'ABM A' },
    { id: 2, name: 'ABM B' },
    { id: 3, name: 'ABM C' }
  ];

  retailerTypes = [
    { id: 1, name: 'Type 1' },
    { id: 2, name: 'Type 2' },
    { id: 3, name: 'Type 3' }
  ];

  rsNames = [
    { id: 1, name: 'RS 1' },
    { id: 2, name: 'RS 2' },
    { id: 3, name: 'RS 3' }
  ];

  brands = [
    { id: 1, name: 'Brand X' },
    { id: 2, name: 'Brand Y' },
    { id: 3, name: 'Brand Z' }
  ];

  // selectedRegions: any[] = [];
  // selectedAbmNames: any[] = [];
  // selectedRetailerTypes: any[] = [];
  selectedRsNames: any[] = [];
  // selectedBrands: any[] = [];
  availableRetailerTypes = [
    { id: 1, name: 'DD' },
    { id: 2, name: 'IDD' }
  ];

  // Holds selected retailer types
  selectedRetailerTypes: { id: number; name: string }[] = [];

  // Control dropdown visibility
  isDropdownOpenForRetailer = false;

  // Search input value
  searchInputValueForRetailer = '';

  // Filtered retailer types based on search input
  filteredRetailerTypesList = this.availableRetailerTypes;

  availableRSNames: any = [
    // { id: 1, name: 'RS Name 1' },
    // { id: 2, name: 'RS Name 2' },
    // { id: 3, name: 'RS Name 3' },
    // { id: 4, name: 'RS Name 4' },
    // { id: 5, name: 'RS Name 5' }
  ];

  // Holds selected RS names
  selectedRSNames: { id: number; name: string }[] = [];

  // Control dropdown visibility
  isDropdownOpenForRS = false;

  // Search input value
  searchInputValueForRS = '';

  // Filtered RS names based on search input
  filteredRSNamesList = this.availableRSNames;
  @ViewChild('chart') chart!: ChartComponent;
  chartOptions!: ApexOptions;
  dashBoardInitalDataOptions!: Partial<ChartOptions>;

  // chartOptionsline!: Partial<ChartOptions>;
  chartOptions_1!: Partial<ChartOptions>;
  chartOptions_2!: Partial<ChartOptions>;
  chartOptions_3!: Partial<ChartOptions>;
  @ViewChild('multiSelect') multiSelect: any;
  public data: any = [];
  public settings = {};
  // public selectedItems = [];
  dropdownOptions = [
    { id: 1, name: 'Option 1' },
    { id: 2, name: 'Option 2' },
    { id: 3, name: 'Option 3' },
    { id: 4, name: 'Option 4' }
  ];
  selectedOptions: any[] = [];

  availableBrands: any = [
    // { id: 1, name: 'Brand A' },
    // { id: 2, name: 'Brand B' },
    // { id: 3, name: 'Brand C' },
    // { id: 4, name: 'Brand D' },
    // { id: 5, name: 'Brand E' }
  ];

  availableMonths: any[] = [
    { id: 1, name: 'January', code: 'JAN' },
    { id: 2, name: 'February', code: 'FEB' },
    { id: 3, name: 'March', code: 'MAR' },
    { id: 4, name: 'April', code: 'APR' },
    { id: 5, name: 'May', code: 'MAY' },
    { id: 6, name: 'June', code: 'JUN' },
    { id: 7, name: 'July', code: 'JUL' },
    { id: 8, name: 'August', code: 'AUG' },
    { id: 9, name: 'September', code: 'SEP' },
    { id: 10, name: 'October', code: 'OCT' },
    { id: 11, name: 'November', code: 'NOV' },
    { id: 12, name: 'December', code: 'DEC' }
  ];

  // Filtered list for search functionality
filteredMonthsList: any[] = [...this.availableMonths];
selectedMonths: any[] = [];
isDropdownOpenForMonth: boolean = false;
areAllMonthsSelected: boolean = false;
searchInputValueForMonth: string = '';




  // Holds selected brands
  selectedBrands: { id: number; name: string }[] = [];
  

  // Control dropdown visibility
  isDropdownOpenForBrand = false;
  // Search input value
  searchInputValueForBrand = '';

  // Filtered brands based on search input
  filteredBrandsList = this.availableBrands;

  isLoading = false; // Start with loading state
  isMobileView = window.innerWidth <= 768;

  // constructor
  constructor(
    public dashboardService: DashboardService,
    private spinner: NgxSpinnerService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    // this.selectedAbmNames = [];
    // this.selectedBrands = [];
    // this.selectedRSNames = [];
    // this.selectedRegions = [];
    // this.selectedRetailerTypes = [];
    // this.selectedMonths = [];

    this.route.queryParams.subscribe((params) => {
      this.dashboardService.selectedData = params['id'];
         if (params['id'] === '1') {
        this.dashBoardInitalDataFn();
      } else if (params['id']) {
        this.prepareSearchData('reset');
      }
    });
  }

  // life cycle event
  async ngOnInit() {
    console.log('calling count')
    this.spinner.show();
  }

  // Function to generate payload dynamically
// generateFinancialYearPayload(months:any) {
//   const year = new Date().getFullYear(); // Gets current year
//   const startMonth = months[0]; // Assuming January is the first month in the list
//   const endMonth = months[months.length - 1]; // Assuming December is the last month in the list

//   // Construct startDate and endDate in YYYYMMDD format as integers
//   const startDate = months && months.length ===0 ? 20240401 :parseInt(`${year}0${startMonth.id}01`); // 1st of January
//   const endDate = months && months.length ===0 ? 202401030 : parseInt(`${year}0${endMonth.id}30`); // 30th of December (custom end date as per requirement)

//   return {
//       startDate,
//       endDate,
//   };
// }

generateFinancialYearPayload(months:any) {
  const year = new Date().getFullYear(); // Gets the current year
  
  if (!months || months.length === 0) {
    // Default dates if months array is empty
    return { startDate: 20240401, endDate: 202401030 };
  }

  // Find the minimum and maximum month IDs in the array
  let startMonth = months[0];
  let endMonth = months[0];

  months.forEach((month:any) => {
    if (month.id < startMonth.id) {
      startMonth = month;
    }
    if (month.id > endMonth.id) {
      endMonth = month;
    }
  });

  // Construct startDate and endDate in YYYYMMDD format as integers
  const startDate = parseInt(`${year}0${startMonth.id}01`); // 1st day of the start month
  const endDate = parseInt(`${year}0${endMonth.id}30`);     // 30th day of the end month

  return {
    startDate,
    endDate,
  };
}

  // this is to get the searched data;
  public prepareSearchData(data: any) {
    this.resetChartOptions();
    let MonthlyToalOrdaringPayload: any = {};
    let abmNameList: any = '';
    let brandList: any = '';
    let rsNameList: any = '';
    let regionList: any = '';
    let retailerTypeList: any = '';
    let payloadForMaster:any;
    if (data === 'search') {
      // Prepare comma-separated strings for each array
      abmNameList = this.selectedAbmNames.map((item) => item.id).join(', ');
      brandList = this.selectedBrands.map((item) => item.name).join(', ');
      rsNameList = this.selectedRSNames.map((item) => item.name).join(', ');
      regionList = this.selectedRegions.map((item) => item.name).join(', ');
      retailerTypeList = this.selectedRetailerTypes.map((item) => item.name).join(', ');
      const payload = this.generateFinancialYearPayload(this.selectedMonths);

      MonthlyToalOrdaringPayload = {
        regionList: regionList && regionList.length > 0 ? regionList : "EAST, WEST,NORTH,SOUTH 1,SOUTH 2", /// default all regions
        startDate: payload.startDate, /// default case start date of financial year in integer format
        endDate: payload.endDate, ////  default case end date of financial year in integer format
        brandList: brandList, //// default casen ""
        rsNameList: rsNameList, //// default casen ""
        abmName:abmNameList,
        retailerType:retailerTypeList,
      };
      payloadForMaster = {
        "regionList": regionList,
        "abmName": abmNameList
        };
    } else {
      MonthlyToalOrdaringPayload  = {
        "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
          "startDate":20240401,       /// default case start date of financial year in integer format
          "endDate": 202401030,       ////  default case end date of financial year in integer format
          "brandList": "",       //// default casen ""
          "rsNameList": "",//// default casen ""
          "abmName":"",
          "retailerType":""
        };
        payloadForMaster = {
          "regionList": "",
          "abmName": ""
          };
        this.selectedAbmNames = [];
        this.selectedBrands = [];
        this.selectedRSNames = [];
        this.selectedRegions = [];
        this.selectedRetailerTypes = [];
        this.selectedMonths = [];
    }
    this.GetMasterData(payloadForMaster,MonthlyToalOrdaringPayload);
  }
  

  cards = [
    {
      background: 'bg-c-blue',
      title: 'East',
      icon: 'icon-shopping-cart',
      number: 'Loading...',
      text: 'Total qty',
      order: 'Loading...',
      qtyText: 'Value',
      qtyValue: 'Loading...',
      dealercount: 'Loading...',
      no: 'Loading...'
    },
    {
      background: 'bg-c-green',
      title: 'North',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      qtyValue: 'Loading...',
      qtyText: 'Loading...',
      number: 'Loading...',
      no: 'Loading...'
    },
    {
      background: 'bg-c-yellow',
      title: 'South1',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: 'Loading...',
      qtyValue: 'Loading...',
      qtyText: 'Loading...',
      no: 'Loading...'
    },
    {
      background: 'bg-c-red',
      title: 'South2',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: 'Loading...',
      no: 'Loading...',
      qtyValue: 'Loading...',
      qtyText: 'Loading...'
    },
    {
      background: 'bg-c-red',
      title: 'West',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: 'Loading...',
      no: 'Loading...',
      qtyValue: 'Loading...',
      qtyText: 'Loading...'
    }
  ];

  images = [
    {
      src: 'assets/images/gallery-grid/img-grd-gal-1.jpg',
      title: 'Old Scooter',
      size: 'PNG-100KB'
    },
    {
      src: 'assets/images/gallery-grid/img-grd-gal-2.jpg',
      title: 'Wall Art',
      size: 'PNG-150KB'
    },
    {
      src: 'assets/images/gallery-grid/img-grd-gal-3.jpg',
      title: 'Microphone',
      size: 'PNG-150KB'
    }
  ];

  public MonthlyToalOrdaring = (MonthlyToalOrdaringPayload?: any) => {
    this.spinner.show();

          // Define month names for easy mapping
          const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

          // Prepare data for each series
          const retailersData :any= [];
          const quantityData :any= [];
          const valueData:any = [];
          const categories: string[] = []; // This will hold the month names
          const isMobile = window.innerWidth <= 768;

    this.chartOptionsline = {
      series: [
           {
          name: 'Retailers',
          data: retailersData // Extracts totalRetailerCode values
        },
        {
          name: 'Quantity (k)',
          data: quantityData // Extracts totalQTY values
        },
        {
          name: 'Value (Cr)',
          data: valueData // Extracts totalRevenue values
        }
      ],
      chart: {
        height: 500,
        type: 'line'
      },
      // dataLabels: {
      //   enabled: true
      // },
      stroke: {
        width: 5,
        curve: 'smooth', // Smooth curve for the line chart
        dashArray: [0, 8, 5] // Different dash arrays for each line
      },
      title: {
        text: 'Monthly Trend Loading...',
        align: 'center'
      },
      // subtitle: {
      //   text: 'No of Re-retailers, Value, and Qty',
      //   align: 'center',
      //   style: {
      //     fontSize: '14px',
      //     color: '#666'
      //   }
      // },
      // legend: {
      //   tooltipHoverFormatter: function (val, opts) {
      //     return val + ' - <strong>' + opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] + '</strong>';
      //   }
      // },
      legend: {
        tooltipHoverFormatter: function (val, opts) {
          const dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
          const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
          return val + ' - <strong>' + formattedValue + '</strong>';
        }
      },  
      dataLabels: {
        enabled:isMobile ? false: true,
        
        offsetX: -5,  // X-axis offset
        style: {
          fontSize: '10px',
        },
        background: {
          enabled: true,
          foreColor: '#000000'
        },
        formatter: function (val: any, { seriesIndex, w }) {
          // Apply custom scaling to data labels based on series name
          const seriesName = w.config.series[seriesIndex].name;
          let scaledValue = val;
    
          // Apply custom scaling for each series
          if (seriesName === 'Quantity (k)') {
            scaledValue = (val * 10).toFixed(2); // Scale Quantity by 10
          } else if (seriesName === 'Retailers') {
            scaledValue = (val * 100).toFixed(0); // Scale Retailers by 100
          } else {
            scaledValue = val.toFixed(2); // No scaling for other series
          }
    
          // Offset Y based on series index
          const dataLabelOffsetsY = [0, 35, 0];  // Customize offsets per series index
          const offsetY = dataLabelOffsetsY[seriesIndex] || 0; // Use the default offset if none provided
    
          // Update the offsetY dynamically
          w.config.dataLabels.offsetX = offsetY;
          return scaledValue;
        }
      },          
      markers: {
        size: 5, // Size of markers on the line
        hover: {
          sizeOffset: 6
        }
      },
      xaxis: {
        labels: {
          // trim: false,
          show: false
        },
        categories: categories,
        title: {
          text: 'Monthly Distribution Loading...', // Left y-axis title
          style: {
            color: '#000000' // Change color as needed
          }
        },
      },
      yaxis: [
        {
          title: {
            text: 'Quantity (K) Loading...', // Left y-axis title
            style: {
              color: '#000000' // Change color as needed
            }
          },
          min: 0, // Minimum value for left y-axis
          labels: {
            show:false,
            formatter: function (val) {
              // return '' + val; // Format for value
              return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
            }
          },
          tickAmount: 4 // Adjust the number of ticks as necessary
        },
        // {
        //   opposite: true, // Make this y-axis on the opposite side
        //   title: {
        //     text: 'Value (Cr)', // Right y-axis title
        //     style: {
        //       color: '#000000' // Change color as needed
        //     }
        //   },
        //    labels: {
        //     show: false,
        //     formatter: function (val) {
        //       // return '' + val; // Format for value
        //       return val.toFixed(0);
        //     }
        //   },
        //   min: 0, // Minimum value for right y-axis
        //   tickAmount: 4 // Adjust the number of ticks as necessary
        // }
      ],
      tooltip: {
        shared: true, // Show shared tooltip
        intersect: false,
        y: [
          {
            title: {
              formatter: function (val) {
                return val;
              }
            }
          },
          {
            title: {
              formatter: function (val) {
                return val;
              }
            }
          },
          {
            title: {
              formatter: function (val) {
                return  val; // Format for value
              }
            }
          }
        ]
      }
    };
    this.dashboardService.MonthlyToalOrdaring(MonthlyToalOrdaringPayload).subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();
          this.GrowthOverPreviousMonth(MonthlyToalOrdaringPayload);

          response.body.forEach((item: any) => {
            // Push values to each series array
            retailersData.push(item.totalRetailerCode/100);
            quantityData.push(item.totalQTY /10000);
            valueData.push(item.totalRevenue / 10000000);
            //   retailersData.push((item.totalRetailerCode).toFixed(2));
            // quantityData.push((item.totalQTY /10000000).toFixed(2));
            // valueData.push((item.totalRevenue / 10000000).toFixed(2));

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });
          // this.chartOptionsline = {
          //   series: [
          //     // {
          //     //   name: 'Retailers',
          //     //   data: [120, 150, 130] // Example data for Retailers over 3 months
          //     // },
          //     // {
          //     //   name: 'Quantity (k)',
          //     //   data: [30, 25, 35] // Example data for Quantity over 3 months
          //     // },
          //     // {
          //     //   name: 'Value',
          //     //   data: [100, 70, 80] // Example data for Value over 3 months (in thousands)
          //     // }
          //     {
          //       name: 'Quantity (k)',
          //       data: quantityData // Extracts totalQTY values
          //     },
          //     {
          //       name: 'Retailers',
          //       data: retailersData // Extracts totalRetailerCode values
          //     },
             
          //     {
          //       name: 'Value (Cr)',
          //       data: valueData // Extracts totalRevenue values
          //     }
          //   ],
          //   chart: {
          //     height: 500,
          //     type: 'line',
          //   },
          //   dataLabels: {
          //     enabled: true
          //   },
          //   stroke: {
          //     width: 5,
          //     curve: 'smooth', // Smooth curve for the line chart
          //     dashArray: [0, 8, 5] // Different dash arrays for each line
          //   },
          //   title: {
          //     text: 'Monthly Trend',
          //     align: 'center'
          //   },
          //   // subtitle: {
          //   //   text: 'No of Re-retailers, Value, and Qty',
          //   //   align: 'center',
          //   //   style: {
          //   //     fontSize: '14px',
          //   //     color: '#666'
          //   //   }
          //   // },
          //   // legend: {
          //   //   tooltipHoverFormatter: function (val, opts) {
          //   //     return val + ' - <strong>' + opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] + '</strong>';
          //   //   }
          //   // },
          //   legend: {
          //     tooltipHoverFormatter: function (val, opts) {
          //       const dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
          //       const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
          //       return val + ' - <strong>' + formattedValue + '</strong>';
          //     }
          //   },            
          //   markers: {
          //     size: 5, // Size of markers on the line
          //     hover: {
          //       sizeOffset: 6
          //     }
          //   },
          //   xaxis: {
          //     labels: {
          //       trim: false
          //     },
          //     categories: categories
          //   },
          //   yaxis: [
          //     {
          //       title: {
          //         text: 'Quantity (K)', // Left y-axis title
          //         style: {
          //           color: '#000000' // Change color as needed
          //         }
          //       },
          //       min: 0, // Minimum value for left y-axis
          //       labels: {
          //         formatter: function (val) {
          //           // return '' + val; // Format for value
          //           return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
          //         }
          //       },
          //       tickAmount: 5 // Adjust the number of ticks as necessary
          //     },

          //     { // Make this y-axis on the opposite side
          //       opposite: true,
          //       title: {
          //         text: 'Value (Cr)', // Right y-axis title
          //         style: {
          //           color: '#000000' // Change color as needed
          //         }
          //       },
          //        labels: {
          //         formatter: function (val) {
          //           // return '' + val; // Format for value
          //           return val.toFixed(0);
          //         }
          //       },
          //       min: 0, // Minimum value for right y-axis
          //       tickAmount: 5 // Adjust the number of ticks as necessary
          //     }
          //   ],
          //   tooltip: {
          //     shared: true, // Show shared tooltip
          //     intersect: false,
          //     y: [
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return val;
          //           }
          //         }
          //       },
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return val;
          //           }
          //         }
          //       },
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return  val; // Format for value
          //           }
          //         }
          //       }
          //     ]
          //   }
          // };
      
      // this is for single y -axis data c
      
          // this.chartOptionsline = {
          //   series: [
          //     {
          //       name: 'Quantity (k)',
          //       data: quantityData // Extracts totalQTY values
          //     },
          //     {
          //       name: 'Retailers',
          //       data: retailersData // Extracts totalRetailerCode values
          //     },
          //     {
          //       name: 'Value (Cr)',
          //       data: valueData // Extracts totalRevenue values
          //     }
          //   ],
          //   chart: {
          //     height: 500,
          //     type: 'line',
          //   },
          //   dataLabels: {
          //     enabled: true
          //   },
          //   stroke: {
          //     width: 5,
          //     curve: 'smooth',
          //     dashArray: [0, 8, 5]
          //   },
          //   title: {
          //     text: 'Monthly Trend',
          //     align: 'center'
          //   },
          //   legend: {
          //     tooltipHoverFormatter: function (val, opts) {
          //       const dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
          //       const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
          //       return val + ' - <strong>' + formattedValue + '</strong>';
          //     }
          //   },
          //   markers: {
          //     size: 5,
          //     hover: {
          //       sizeOffset: 6
          //     }
          //   },
          //   xaxis: {
          //     labels: {
          //       trim: false
          //     },
          //     categories: categories
          //   },
          //   yaxis: {
          //     title: {
          //       text: 'Values', // Generic title for combined y-axis
          //       style: {
          //         color: '#000000'
          //       }
          //     },
          //     min: 0,
          //     labels: {
          //       formatter: function (val) {
          //         return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
          //       }
          //     },
          //     tickAmount: 3
          //   },
          //   tooltip: {
          //     shared: true,
          //     intersect: false,
          //     y: [
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return val;
          //           }
          //         }
          //       },
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return val;
          //           }
          //         }
          //       },
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return val;
          //           }
          //         }
          //       }
          //     ]
          //   }
          // };
          
          // this.chartOptionsline = {
          //   series: [
          //     {
          //       name: 'Quantity (k)',
          //       data: quantityData // Extracts totalQTY values
          //     },
          //     {
          //       name: 'Retailers',
          //       data: retailersData // Extracts totalRetailerCode values
          //     },
          //     {
          //       name: 'Value (Cr)',
          //       data: valueData // Extracts totalRevenue values
          //     }
          //   ],
          //   chart: {
          //     height: 500,
          //     type: 'line',
          //   },
          //   dataLabels: {
          //     enabled: true,
          //   offsetX: -5,
          //   offsetY: -5,  // Move labels slightly to the right of the y-axis
          //     style: {
          //       fontSize: '10px',
          //     },
          //     background: {
          //       enabled: true,
          //       foreColor: '#000000'
          //     }
          //   },
          //   stroke: {
          //     width: 5,
          //     curve: 'smooth',
          //     dashArray: [0, 8, 5]
          //   },
          //   title: {
          //     text: 'Monthly Trend',
          //     align: 'center'
          //   },
          //   legend: {
          //     tooltipHoverFormatter: function (val, opts) {
          //       let dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
          //       if(val == 'Quantity (k)'){
          //         dataValue = dataValue*10;
          //       }else if(val == 'Retailers'){
          //         dataValue = dataValue*100;
          //       }
          //       const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
          //       return val + ' - <strong>' + formattedValue + '</strong>';
          //     }
          //   },
          //   markers: {
          //     size: 5,
          //     hover: {
          //       sizeOffset: 6
          //     }
          //   },
          //   xaxis: {
          //     labels: {
          //       trim: false
          //     },
          //     categories: categories
          //   },
          //   yaxis: {
          //     title: {
          //       text: 'Values',
          //       style: {
          //         color: '#000000'
          //       }
          //     },
          //     min: 0,
          //     labels: {
          //       formatter: function (val) {
          //         return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
          //       },
          //       offsetX: -20, // Move y-axis labels slightly to avoid overlap
          //     },
          //     tickAmount: 3
          //   },
          //   tooltip: {
          //     shared: true,
          //     intersect: false,
          //     y: [
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return val;
          //           }
          //         }
          //       },
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return val;
          //           }
          //         }
          //       },
          //       {
          //         title: {
          //           formatter: function (val) {
          //             return val;
          //           }
          //         }
          //       }
          //     ]
          //   }
          // };

          this.chartOptionsline = {
            series: [
              {
                name: 'Quantity (k)',
                data: quantityData // Extracts totalQTY values
              },
              {
                name: 'Retailers',
                data: retailersData // Extracts totalRetailerCode values
              },
              {
                name: 'Value (Cr)',
                data: valueData // Extracts totalRevenue values
              }
            ],
            chart: {
              height: 500,
              type: 'line',
              zoom: {
                enabled: false
              },
              toolbar: {
                show: true,
                tools: {
                  zoom: true,           // Enable zoom
                  zoomin: true,          // Enable zoom-in
                  zoomout: true,         // Enable zoom-out
                  pan: true,             // Enable panning
                  reset: true            // Reset zoom and pan to the initial state
                },
                // autoSelected: 'zoom'    // Set default tool to zoom
              },
              // events: {
              //   mounted: function (chart) {
              //     // Disable mouse wheel zooming after the chart is mounted
              //     const chartElement = chart.el;
              //     chartElement.addEventListener('wheel', function (event:any) {
              //       event.preventDefault(); // Prevent zooming on wheel scroll
              //     }, { passive: false });
              //   }
              // }
            },
            dataLabels: {
              // enabled: true,
              enabled: isMobile ? false: true,
              offsetX: -5,  // X-axis offset
              style: {
                fontSize: '10px',
              },
              background: {
                enabled: true,
                foreColor: '#000000'
              },
              formatter: function (val: any, { seriesIndex, w }) {
                // Apply custom scaling to data labels based on series name
                const seriesName = w.config.series[seriesIndex].name;
                let scaledValue = val;
          
                // Apply custom scaling for each series
                if (seriesName === 'Quantity (k)') {
                  scaledValue = (val * 10).toFixed(2); // Scale Quantity by 10
                } else if (seriesName === 'Retailers') {
                  scaledValue = (val * 100).toFixed(0); // Scale Retailers by 100
                } else {
                  scaledValue = val.toFixed(2); // No scaling for other series
                }
          
                // Offset Y based on series index
                const dataLabelOffsetsY = [0, 35, 0];  // Customize offsets per series index
                const offsetY = dataLabelOffsetsY[seriesIndex] || 0; // Use the default offset if none provided
          
                // Update the offsetY dynamically
                w.config.dataLabels.offsetX = offsetY;
                return scaledValue;
              }
            },
            stroke: {
              width: 5,
              curve: 'smooth',
              dashArray: [0, 8, 5]
            },
            title: {
              text: 'Monthly Trend',
              align: 'center'
            },
            legend: {
              tooltipHoverFormatter: function (val, opts) {
                // Apply custom scaling in the legend tooltip
                let dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
                if (val === 'Quantity (k)') {
                  dataValue = dataValue * 10;
                } else if (val === 'Retailers') {
                  dataValue = dataValue * 100;
                }
                const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
                return val + ' - <strong>' + formattedValue + '</strong>';
              }
            },
            markers: {
              size: 5,
              hover: {
                sizeOffset: 6
              }
            },
            xaxis: {
              labels: {
                trim: false
              },
              categories: categories
            },
            yaxis: {
              title: {
                text: 'Values',
                style: {
                  color: '#000000'
                }
              },
              min: 0,
              labels: {
                formatter: function (val) {
                  return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
                },
                offsetX: -20
              },
              tickAmount: 3
            },
            tooltip: {
              shared: true,
              intersect: false,
              y: {
                formatter: function (value, { series, seriesIndex }) {
                  // Apply scaling based on series name in the tooltip
                  const seriesName = series[seriesIndex].name;
                  if (seriesIndex === 0) {
                    return (value * 10).toFixed(2);  // Scale Quantity by 10
                  } else if (seriesIndex === 1) {
                    return (value * 100).toFixed(0);  // Scale Retailers by 100
                  }
                  return value.toFixed(2);  // No scaling for other series
                }
              }
            }
          };
          
          
        
          
          
        }
        console.log('this is for checking');
      },
      (error) => {
        // this.errorMessage = 'An error occurred during login';
        console.error('Login error:', error);
      }
    );
  };

  public GrowthOverPreviousMonth = (MonthlyToalOrdaringPayload?: any) => {
    this.spinner.show();
    
          // Define month names for easy mapping
          const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

          // Prepare data for each series
          const retailersData: any = [];
          const quantityData: any = [];
          const valueData: any = [];
          const categories: string[] = []; // This will hold the month names
          this.chartOptions = {
            chart: {
              type: 'bar',
              height: 500,
              toolbar: {
                show: true,
                tools: {
                  zoom: true,           // Enable zoom
                  zoomin: true,         // Enable zoom-in
                  zoomout: true,        // Enable zoom-out
                  pan: true,            // Enable panning
                  reset: true           // Reset zoom and pan to the initial state
                },
                autoSelected: 'zoom'    // Set default tool to zoom
              }
            },
            title: {
              text: 'Growth Over Previous Month Loading...', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                // fontWeight: 'bold',
                color: '#333'
              }
            },
            legend: {
              tooltipHoverFormatter: function (val, opts) {
                const dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
                const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
                return val + ' - <strong>' + formattedValue + '</strong>';
              }
            }, 
            series: [
              {
                name: 'Number of Retailers',
                data: retailersData // Example values for Number of Retailers
              },
              {
                name: 'Quantity (k)',
                data: quantityData // Example values for Quantity
              },
              {
                name: 'Value (Cr)',
                data: valueData // Example values for Value
              }
            ],
            plotOptions: {
              bar: {
                horizontal: false,
                columnWidth: '80%'
                // endingShape: "rounded"
              }
            },
            dataLabels: {
              enabled: this.isMobileView ? false: true,
              background: {
                enabled: true,
                foreColor: '#000000'
              },
            },
            stroke: {
              show: true,
              width: 2,
              colors: ['transparent']
            },
            xaxis: {
              categories: categories,
              title: {
                text: 'Monthly Distribution Loading...', // Left y-axis title
                style: {
                  color: '#000000' // Change color as needed
                }
              },
              labels: {
                // trim: false,
                show: false
              },
            },
            // yaxis: {
            //   title: {
            //     text: ' (Growth Value)'
            //   },
            //               // min: -100 // Set a minimum value for y-axis to accommodate negative values
            // },

            yaxis: [
             
              {
                title: {
                  text: '(Growth Value) Loading...', // Left y-axis title
                  style: {
                    color: '#000000' // Change color as needed
                  }
                },
                // min: 0, // Minimum value for left y-axis
                labels: {
                  show:false,
                  formatter: function (val:any) {
                    return '' + val; // Format for value
                  }
                },
                tickAmount: 1 // Adjust the number of ticks as necessary
              },
            ],
            fill: {
              opacity: 1
            },
            tooltip: {
              y: {
                formatter: function (val) {
                  // return val.toFixed(2);
                  return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);

                }
              }
            }
          };
    this.dashboardService.GrowthOverPreviousMonth(MonthlyToalOrdaringPayload).subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();
          this.RegionWiseMonthlyDistribution(MonthlyToalOrdaringPayload);

          response.body.forEach((item: any) => {
            // Push values to each series array
            // retailersData.push(item.retailerGrowth);
            // quantityData.push(item.orderGrowth);
            // valueData.push(item.priceGrowth);
            retailersData.push(item.retailerGrowth);
            quantityData.push((item.orderGrowth / 1000).toFixed(2)); // Converts orderGrowth to thousands
            valueData.push((item.priceGrowth / 10000000).toFixed(2)); // Converts priceGrowth to crores

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });

          this.chartOptions = {
            chart: {
              zoom: {
                enabled: true
              },
              type: 'bar',
              height: 500,
              toolbar: {
                show: true,
                tools: {
                  zoom: true,           // Enable zoom
                  zoomin: true,         // Enable zoom-in
                  zoomout: true,        // Enable zoom-out
                  pan: true,            // Enable panning
                  reset: true           // Reset zoom and pan to the initial state
                },
                // autoSelected: 'zoom'    // Set default tool to zoom
              }
            },
            title: {
              text: 'Growth Over Previous Month.', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                color: '#333'
              }
            },
            legend: {
              tooltipHoverFormatter: function (val, opts) {
                const dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
                const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
                return val + ' - <strong>' + formattedValue + '</strong>';
              }
            },
            series: [
              {
                name: 'Number of Retailers',
                data: retailersData // Example values for Number of Retailers
              },
              {
                name: 'Value (Cr)',
                data: valueData // Example values for Value
              },
              {
                name: 'Quantity (k)',
                data: quantityData // Example values for Quantity
              },
             
            ],
            plotOptions: {
              bar: {
                horizontal: false,
                columnWidth: '80%',
                dataLabels: {
                  position: 'top'
                }
              }
            },
            dataLabels: {
              // enabled: true,
              enabled: this.isMobileView ? false: true,

              //  offsetY: -10,
              
              style: {
                fontSize: "10px",
                colors: ['#000000'] 
              },
              
            },
            stroke: {
              show: true,
              width: 2,
              colors: ['transparent']
            },
            xaxis: {
              categories: categories // Your x-axis categories (months, or other)
            },
            yaxis: [
              {
                floating: false,
                title: {
                  text: ' (Growth Value)', // Left y-axis title
                  // style: {
                  //   color: '#000000' // Change color as needed
                  // }
                },
                // min: 0, // Set minimum value for the y-axis
                //max: 800, // Set maximum value for the y-axis (adjust as needed)
                tickAmount: 4, // Adjust the number of ticks based on the range
                 labels: {
                   formatter: function (val) {
                     return val.toFixed(0);
                   }
                 }
              }
            ],
            fill: {
              opacity: 1
            },
            tooltip: {
              y: {
                formatter: function (val) {
                  return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
                }
              }
            }
          };
          
          
        }
        console.log('this is for checking');
      },
      (error) => {
        // this.errorMessage = 'An error occurred during login';
        console.error('Login error:', error);
      }
    );
  };

  public openLineORGridView(data: any) {
    this.appendClass = '';
    if (data == 'grid') {
      this.appendClass = data;
    } else {
      this.appendClass = data;
    }
  }

  onSelectOption(option: any) {
    if (this.selectedOptions.includes(option)) {
      this.selectedOptions = this.selectedOptions.filter((item) => item !== option);
    } else {
      this.selectedOptions.push(option);
    }
  }

  isSelected(option: any): boolean {
    return this.selectedOptions.includes(option);
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
    if (!this.isOpen) {
      this.searchTerm = ''; // Reset search term when dropdown is closed
      this.filteredItems = this.items; // Reset filtered items
    }
  }

  toggleSelection(item: string) {
    const index = this.selectedItems.indexOf(item);
    if (index === -1) {
      this.selectedItems.push(item);
    } else {
      this.selectedItems.splice(index, 1);
    }
  }

  selectAll(event: any) {
    if (event.target.checked) {
      this.selectedItems = [...this.items];
    } else {
      this.selectedItems = [];
    }
  }

  filterItems() {
    this.filteredItems = this.items.filter((item) => item.toLowerCase().includes(this.searchTerm.toLowerCase()));
  }

  get allSelected(): boolean {
    return this.selectedItems.length === this.items.length;
  }

  removeItem(item: string, event: MouseEvent) {
    event.stopPropagation(); // Prevent the dropdown from closing
    const index = this.selectedItems.indexOf(item);
    if (index !== -1) {
      this.selectedItems.splice(index, 1); // Remove the item from the selected items
    }
  }

  // Toggle dropdown visibility
  toggleDropdownVisibility() {
    // this.isDropdownOpen = !this.isDropdownOpen;
    this.toggleDropdownVisibility1('isDropdownOpen');
    if (this.isDropdownOpen) {
      this.filteredRegionsList = this.availableRegions; // Reset filtered list on opening
    }
    // this.hideOtherDropDowns('general');
  }
  // Filter regions based on search input
  filterAvailableRegions() {
    this.filteredRegionsList = this.availableRegions.filter((region: any) =>
      region.name.toLowerCase().includes(this.searchInputValue.toLowerCase())
    );
  }

  // Toggle selection of a specific region
  toggleRegionSelection(region: { id: number; name: string }) {
    const index = this.selectedRegions.findIndex((selected) => selected.id === region.id);
    if (index > -1) {
      this.selectedRegions.splice(index, 1); // Remove region if already selected
    } else {
      this.selectedRegions.push(region); // Add region if not selected
    }
    this.getMasterDataForFilter('search');

    // to filter the abms based on the user selection.
    // const FilterAbms = this.availableAbmNames.filter((data: any) => 
    //   data.region && this.selectedRegions.some((selectedRegion: any) => selectedRegion.name === data.region)
    // );
    // this.availableAbmNames = FilterAbms;

  }

  // Remove a selected region
  removeSelectedRegion(region: { id: number; name: string }, event: MouseEvent) {
    event.stopPropagation(); // Prevent dropdown from closing
    this.toggleRegionSelection(region); // Toggle selection
    this.getMasterDataForFilter('search');
  }

  // Select or deselect all regions
  toggleAllRegionsSelection(event: any) {
    this.selectedRegions = event.target.checked ? [...this.availableRegions] : []; // Select/Deselect all
    // to filter the abms based on the user selection.
    // const FilterAbms = this.availableAbmNames.filter((data: any) => 
    //   data.region && this.selectedRegions.some((selectedRegion: any) => selectedRegion.name === data.region)
    // );
    // this.availableAbmNames = FilterAbms;
    this.getMasterDataForFilter('search');
  }

  // Check if all regions are selected
  get areAllRegionsSelected() {
    return this.selectedRegions.length === this.availableRegions.length; // Return true if all regions are selected
  }

  // Toggle dropdown visibility for ABM names
  toggleDropdownVisibilityForAbm() {
    // this.isDropdownOpenForAbm = !this.isDropdownOpenForAbm;
   
    // isDropdownOpenForRS
    // isDropdownOpenForBrand
    // isDropdownOpen
    // isDropdownOpenForAbm
    // this.hideOtherDropDowns('abm');
    this.toggleDropdownVisibility1('isDropdownOpenForAbm');
    if (this.isDropdownOpenForAbm) {
      this.filteredAbmNamesList = this.availableAbmNames; // Reset filtered list on opening
    }
  }

  hideOtherDropDowns(selectedDropdown: any): void {
    //    // Check if the selected dropdown is already open
    // if (this[selectedDropdown]) {
    //   // If it's already open, close it
    //   this[selectedDropdown] = false;
    // } else {
    //   // If it's not open, close all dropdowns and open the selected one
    //   this.isDropdownOpenForRetailer = false;
    //   this.isDropdownOpenForRS = false;
    //   this.isDropdownOpenForBrand = false;
    //   this.isDropdownOpen = false;
    //   this.isDropdownOpenForAbm = false;
    //   // Open the selected dropdown
    //   this[selectedDropdown] = true;
    // }
  }

  toggleDropdownVisibility1(selectedDropdown: DropdownFlag): void {
    // Check if the selected dropdown is already open
    if (this[selectedDropdown]) {
      // If it's already open, close it
      this[selectedDropdown] = false;
    } else {
      // If it's not open, close all dropdowns and open the selected one
      this.isDropdownOpenForRetailer = false;
      this.isDropdownOpenForRS = false;
      this.isDropdownOpenForBrand = false;
      this.isDropdownOpen = false;
      this.isDropdownOpenForAbm = false;
      this.isDropdownOpenForMonth = false;
      // Open the selected dropdown
      this[selectedDropdown] = true;
    }
  }

  // Filter ABM names based on search input
  filterAvailableAbmNames() {
    this.filteredAbmNamesList = this.availableAbmNames.filter((abm: any) =>
      abm.name.toLowerCase().includes(this.searchInputValueForAbm.toLowerCase())
    );
  }

  // Toggle selection of a specific ABM name
  toggleAbmSelection(abm: { id: number; name: string }) {
    const index = this.selectedAbmNames.findIndex((selected) => selected.id === abm.id);
    if (index > -1) {
      this.selectedAbmNames.splice(index, 1); // Remove ABM name if already selected
    } else {
      this.selectedAbmNames.push(abm); // Add ABM name if not selected
    }

    this.getMasterDataForFilter('search');


    // // to filter the abms based on the user selection.
    // const FilterRSNames = this.availableRSNames.filter((data: any) => 
    //   data.region && this.selectedAbmNames.some((selectedAbm: any) => selectedAbm.name === data.region)
    // );
    // this.availableRSNames = FilterRSNames;
  }

  // Remove a selected ABM name
  removeSelectedAbm(abm: { id: number; name: string }, event: MouseEvent) {
    event.stopPropagation(); // Prevent dropdown from closing
    this.toggleAbmSelection(abm); // Toggle selection
    this.getMasterDataForFilter('search');

  }

  // Select or deselect all ABM names
  toggleAllAbmSelection(event: any) {
    this.selectedAbmNames = event.target.checked ? [...this.availableAbmNames] : []; // Select/Deselect all

     // to filter the abms based on the user selection.
     const FilterRSNames = this.availableRSNames.filter((data: any) => 
      data.region && this.selectedAbmNames.some((selectedAbm: any) => selectedAbm.name === data.region)
    );
    this.availableRSNames = FilterRSNames;
    this.getMasterDataForFilter('search');

  }

  // Check if all ABM names are selected
  get areAllAbmSelected() {
    return this.selectedAbmNames.length === this.availableAbmNames.length; // Return true if all ABM names are selected
  }

  // Toggle dropdown visibility for retailer types
  toggleDropdownVisibilityForRetailer() {
    // this.isDropdownOpenForRetailer = !this.isDropdownOpenForRetailer;
    // this.hideOtherDropDowns('retailer');
    this.toggleDropdownVisibility1('isDropdownOpenForRetailer');
    if (this.isDropdownOpenForRetailer) {
      this.filteredRetailerTypesList = this.availableRetailerTypes; // Reset filtered list on opening
    }
  }

  // Filter retailer types based on search input
  filterAvailableRetailerTypes() {
    this.filteredRetailerTypesList = this.availableRetailerTypes.filter((retailer) =>
      retailer.name.toLowerCase().includes(this.searchInputValueForRetailer.toLowerCase())
    );
  }

  // Toggle selection of a specific retailer type
  toggleRetailerSelection(retailer: { id: number; name: string }) {
    const index = this.selectedRetailerTypes.findIndex((selected) => selected.id === retailer.id);
    if (index > -1) {
      this.selectedRetailerTypes.splice(index, 1); // Remove retailer type if already selected
    } else {
      this.selectedRetailerTypes.push(retailer); // Add retailer type if not selected
    }
  }

  // Remove a selected retailer type
  removeSelectedRetailer(retailer: { id: number; name: string }, event: MouseEvent) {
    event.stopPropagation(); // Prevent dropdown from closing
    this.toggleRetailerSelection(retailer); // Toggle selection
  }

  // Select or deselect all retailer types
  toggleAllRetailerSelection(event: any) {
    this.selectedRetailerTypes = event.target.checked ? [...this.availableRetailerTypes] : []; // Select/Deselect all
  }

  // Check if all retailer types are selected
  get areAllRetailerSelected() {
    return this.selectedRetailerTypes.length === this.availableRetailerTypes.length; // Return true if all retailer types are selected
  }

  toggleDropdownVisibilityForRS() {
    // this.isDropdownOpenForRS = !this.isDropdownOpenForRS;
    // this.hideOtherDropDowns('RS');
    this.toggleDropdownVisibility1('isDropdownOpenForRS');
    if (this.isDropdownOpenForRS) {
      this.filteredRSNamesList = this.availableRSNames; // Reset filtered list on opening
    }
  }

  // Filter RS names based on search input
  filterAvailableRSNames() {
    this.filteredRSNamesList = this.availableRSNames.filter((rs: any) =>
      rs.name.toLowerCase().includes(this.searchInputValueForRS.toLowerCase())
    );
  }

  // Toggle selection of a specific RS name
  toggleRSSelection(rs: { id: number; name: string }) {
    const index = this.selectedRSNames.findIndex((selected) => selected.id === rs.id);
    if (index > -1) {
      this.selectedRSNames.splice(index, 1); // Remove RS name if already selected
    } else {
      this.selectedRSNames.push(rs); // Add RS name if not selected
    }
  }

  // Remove a selected RS name
  removeSelectedRS(rs: { id: number; name: string }, event: MouseEvent) {
    event.stopPropagation(); // Prevent dropdown from closing
    this.toggleRSSelection(rs); // Toggle selection
  }

  // Select or deselect all RS names
  toggleAllRSSelection(event: any) {
    this.selectedRSNames = event.target.checked ? [...this.availableRSNames] : []; // Select/Deselect all
  }

  // Check if all RS names are selected
  get areAllRSSelected() {
    return this.selectedRSNames.length === this.availableRSNames.length; // Return true if all RS names are selected
  }

  // Toggle dropdown visibility for Brands
  toggleDropdownVisibilityForBrand() {
    // this.isDropdownOpenForBrand = !this.isDropdownOpenForBrand;
    // this.hideOtherDropDowns('brand');
    this.toggleDropdownVisibility1('isDropdownOpenForBrand');
    if (this.isDropdownOpenForBrand) {
      this.filteredBrandsList = this.availableBrands; // Reset filtered list on opening
    }
  }

  // Filter Brands based on search input
  filterAvailableBrands() {
    this.filteredBrandsList = this.availableBrands.filter((brand: any) =>
      brand.name.toLowerCase().includes(this.searchInputValueForBrand.toLowerCase())
    );
  }

  // Toggle selection of a specific Brand
  toggleBrandSelection(brand: { id: number; name: string }) {
    const index = this.selectedBrands.findIndex((selected) => selected.id === brand.id);
    if (index > -1) {
      this.selectedBrands.splice(index, 1); // Remove Brand if already selected
    } else {
      this.selectedBrands.push(brand); // Add Brand if not selected
    }
  }

  // Remove a selected Brand
  removeSelectedBrand(brand: { id: number; name: string }, event: MouseEvent) {
    event.stopPropagation(); // Prevent dropdown from closing
    this.toggleBrandSelection(brand); // Toggle selection
  }

  // Select or deselect all Brands
  toggleAllBrandSelection(event: any) {
    this.selectedBrands = event.target.checked ? [...this.availableBrands] : []; // Select/Deselect all
  }

  // Check if all Brands are selected
  get areAllBrandsSelected() {
    return this.selectedBrands.length === this.availableBrands.length; // Return true if all Brands are selected
  }

  GetMasterData(payload:any,MonthlyToalOrdaringPayload?:any,isFromFilter?:any): void {
    // this.spinner.show();
    this.dashboardService.GetMasterData(payload).subscribe(
      (response:any) => {
        // this.spinner.hide();
        if(!isFromFilter){

          if(this.dashboardService.selectedData === '2'){
            this.MonthlyToalOrdaring(MonthlyToalOrdaringPayload);
          } else if(this.dashboardService.selectedData === '3'){
            this.chartOptionslineForOrdBhFn(MonthlyToalOrdaringPayload);
          } else if(this.dashboardService.selectedData === '4'){
            MonthlyToalOrdaringPayload.startDate = 20241001;       /// default case start date of financial year in integer format
            MonthlyToalOrdaringPayload.endDate = 202401030;
            this.percentageOfOrdersOfDayInMonth(MonthlyToalOrdaringPayload);
          }
        }

        if (response && response.body && response.body.region && response.body.region.length > 0) {
          const availableRegions = response.body.region.map((region: any, index: any) => {
            return {
              id: index + 1, // Assign unique IDs starting from 1
              name: region.replace(' ', '') // Replace space in 'SOUTH 1' and 'SOUTH 2' with empty string
            };
          });
          this.availableRegions = availableRegions;
        }
        if (response && response.body&& response.body.brand && response.body.brand.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableBrands: Brand[] = response.body.brand.map(([id, name]: [string, string]) => {
            return {
              id: parseInt(id), // Convert ID to a number
              name: name // Keep the name as is
            };
          });
          this.availableBrands = availableBrands;
        }

        if (response && response.body && response.body.rsName && response.body.rsName.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableRSNames: Brand[] = response.body.rsName.map(([name, userid,region]: [string, string,string,string]) => {
            return {
              id: userid, // Convert ID to a number
              name: name,
              region:region,
              // Keep the name as is
            };
          });
          this.availableRSNames = availableRSNames;
        }
        if (response &&response.body&& response.body.abmName && response.body.abmName.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableAbmNames: Brand[] = response.body.abmName.map(([id, name,region]: [string, string,string]) => {
            return {
              id: id, // Convert ID to a number
              name: name,// Keep the name as is
              region:region
            };
          });
          this.availableAbmNames = availableAbmNames;
        }
      },
      (error) => {
        // this.errorMessage = 'An error occurred during login';
        console.error('Login error:', error);
      }
    );
  }

  public dashBoardInitalDataFn = async (data?: any) => {
    this.spinner.show();
    this.dashboardService.dashBoardInitalData().subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();
          this.cards[0].number = response.body[0].totalOrder;
          this.cards[0].qtyValue = response.body[0].orderValue;
          this.cards[0].dealercount = response.body[0].delears;
          this.cards[0].no = response.body[0].orderQuentity;
          


          this.cards[1].number = response.body[1].totalOrder;
          this.cards[1].qtyValue = response.body[1].orderValue;
          this.cards[1].dealercount = response.body[1].delears;
          this.cards[1].no = response.body[1].orderQuentity;

          this.cards[2].number = response.body[2].totalOrder;
          this.cards[2].qtyValue = response.body[2].orderValue;
          this.cards[2].dealercount = response.body[2].delears;
          this.cards[2].no = response.body[2].orderQuentity;


          this.cards[3].number = response.body[3].totalOrder;
          this.cards[3].qtyValue = response.body[3].orderValue;
          this.cards[3].dealercount = response.body[3].delears;
          this.cards[3].no = response.body[3].orderQuentity;

          this.cards[4].number = response.body[4].totalOrder;
          this.cards[4].qtyValue = response.body[4].orderValue;
          this.cards[4].dealercount = response.body[4].delears;
          this.cards[4].no = response.body[4].orderQuentity;

// this.dashBoardInitalDataforchart();
          // // Define month names for easy mapping
          // const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

          // // Prepare data for each series
          // const retailersData: any = [];
          // const quantityData: any = [];
          // const valueData: any = [];
          // const categories: string[] = []; // This will hold the month names

          // response.body.forEach((item: any) => {
          //   // Push values to each series array
          //   retailersData.push(item.totalRetailerCode);
          //   quantityData.push(item.totalQTY);
          //   valueData.push(item.totalRevenue);

          //   // Get month name and add to categories
          //   categories.push(monthNames[item.month - 1]);
          // });

          // this.dashBoardInitalDataOptions = {
          //   chart: {
          //     type: 'bar',
          //     height: 350,
          //     toolbar: {
          //       show: false // Optional: Hide toolbar if not needed
          //     }
          //   },
          //   title: {
          //     text: 'Orders Monthly Details.', // Chart title
          //     align: 'center',
          //     style: {
          //       fontSize: '16px',
          //       fontWeight: 'bold',
          //       color: '#333'
          //     }
          //   },
          //   series: [
          //     {
          //       name: 'Order Qty',
          //       data: retailersData // Example values for Number of Retailers
          //     },
          //     {
          //       name: 'Order Value',
          //       data: quantityData // Example values for Quantity
          //     }
          //   ],
          //   plotOptions: {
          //     bar: {
          //       horizontal: false,
          //       columnWidth: '60%'
          //       // endingShape: "rounded"
          //     }
          //   },
          //   dataLabels: {
          //     enabled: true
          //   },
          //   stroke: {
          //     show: true,
          //     width: 2,
          //     colors: ['transparent']
          //   },
          //   xaxis: {
          //     categories: categories
          //   },
          //   yaxis: {
          //     title: {
          //       text: ' (Orders)'
          //     }
          //     // min: -100 // Set a minimum value for y-axis to accommodate negative values
          //   },
          //   fill: {
          //     opacity: 1
          //   },
          //   tooltip: {
          //     y: {
          //       formatter: function (val) {
          //         return '' + val + ' ';
          //       }
          //     }
          //   }
          // };
        }
        console.log('this is for checking');
      },
      (error) => {
        // this.errorMessage = 'An error occurred during login';
        console.error('Login error:', error);
      }
    );
  };
  

  loadData() {
    // Simulate a delay for loading data
    setTimeout(() => {
      // Data is loaded, hide the spinner
      this.isLoading = false;
    }, 3000); // Adjust this timeout as needed
  }

  public dashBoardInitalDataforchart = async (data?: any) => {
    this.spinner.show();
    this.dashboardService.dashboardGraph().subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();
         
          // Define month names for easy mapping
          const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

          // Prepare data for each series
          const retailersData: any = [];
          const quantityData: any = [];
          const valueData: any = [];
          const categories: string[] = []; // This will hold the month names

          response.body.forEach((item: any) => {
            // Push values to each series array
            retailersData.push(item.totalOrder);
            quantityData.push(item.orderValue);
            valueData.push(item.totalRevenue);

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });

          this.dashBoardInitalDataOptions = {
            chart: {
              type: 'bar',
              height: 500,
              toolbar: {
                show: false // Optional: Hide toolbar if not needed
              }
            },
            title: {
              text: 'Orders Monthly Details.', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                // fontWeight: 'bold',
                color: '#333'
              }
            },
            series: [
              {
                name: 'Order Qty',
                data: retailersData // Example values for Number of Retailers
              },
              {
                name: 'Order Value',
                data: quantityData // Example values for Quantity
              }
            ],
            plotOptions: {
              bar: {
                horizontal: false,
                columnWidth: '60%'
                // endingShape: "rounded"
              }
            },
            dataLabels: {
              // enabled: true
              enabled: this.isMobileView ? false: true,

            },
            stroke: {
              show: true,
              width: 2,
              colors: ['transparent']
            },
            xaxis: {
              categories: categories
            },
            yaxis: {
              title: {
                text: ' (Order value)'
              }
              // min: -100 // Set a minimum value for y-axis to accommodate negative values
            },
            fill: {
              opacity: 1
            },
            tooltip: {
              y: {
                formatter: function (val) {
                  return '' + val + ' ';
                }
              }
            }
          };
        }
        console.log('this is for checking');
      },
      (error) => {
        // this.errorMessage = 'An error occurred during login';
        console.error('Login error:', error);
      }
    );
  };

public RegionWiseMonthlyDistribution = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  const categories: string[] = [];
  const isMobile = window.innerWidth <= 768;

  // Define chart options with placeholder series (to be updated dynamically)
  this.RegionWiseMonthlyDistibutionOptions = {
    // series: [],
    series: [
      // Retailers group
      { name: 'Retailers - East', group: 'retailers', data: [] },
      { name: 'Retailers - South1', group: 'retailers', data: [] },
      { name: 'Retailers - South2', group: 'retailers', data: [] },
      { name: 'Retailers - North', group: 'retailers', data: [] },
      { name: 'Retailers - West', group: 'retailers', data: [] },

      // Qty group
      { name: 'Qty - East', group: 'qty', data: [] },
      { name: 'Qty - South1', group: 'qty', data: [] },
      { name: 'Qty - South2', group: 'qty', data: [] },
      { name: 'Qty - North', group: 'qty', data: [] },
      { name: 'Qty - West', group: 'qty', data: [] },

      // Value group
      { name: 'Value - East', group: 'value', data: [] },
      { name: 'Value - South1', group: 'value', data: [] },
      { name: 'Value - South2', group: 'value', data: [] },
      { name: 'Value - North', group: 'value', data: [] },
      { name: 'Value - West', group: 'value', data: [] }
    ],
    title: {
      text: 'Region Wise Distribution Loading...',
      align: 'center',
      // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
    },
    yaxis: [
      {
        title: {
          text: '(Quantity) Loading...',
          style: { color: '#000000' }
        },
        labels: {
          show:false,
          formatter: (val: any) => '' + val
        },
        tickAmount: 4
      }
    ],
    xaxis: {
      labels: { trim: false,show:false },
      categories: [],
      title: { style: { color: '#000000' } }
    },
    dataLabels: {
      //  enabled: true ,
       enabled: this.isMobileView ? false: true,

      style: {
        fontSize: "10px",
        colors: ['#000000'] 
      },
    },
    chart: {
      type: 'bar',
      height: 500,
      stacked: true
    },
    plotOptions: { bar: { horizontal: false } },
    fill: { opacity: 1 },
    colors: [
      '#80c7fd',
      '#008FFB',
      '#80f1cb',
      '#00E396',
      '#feb019', // Retailers colors
      '#FF5733',
      '#FFBD33',
      '#C70039',
      '#900C3F',
      '#581845', // Qty colors
      '#2ECC71',
      '#28B463',
      '#239B56',
      '#1D8348',
      '#186A3B' // Value colors
    ],
    legend: { position: isMobile ? 'top' : 'right', horizontalAlign: 'left' },
  };
  this.dashboardService.RegionWiseMonthlyDistribution(MonthlyTotalOrderingPayload).subscribe(
      (response) => {
          if (response && response.body) {
              this.spinner.hide();
              this.RegionWiseGrowthOverPreviousMonth(MonthlyTotalOrderingPayload);
              // Prepare categories based on unique months
              const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
              uniqueMonths.forEach((month: any) => {
                  categories.push(monthNames[month - 1]);
              });

              // Prepare data series for each metric and region
              const prepareSeriesData = (data: any[], metric: string, group: string) => {
                  const regions = ['EAST', 'WEST', 'NORTH', 'SOUTH 1', 'SOUTH 2'];
                  const metricLabels: { [key: string]: string } = {
                      totalRetailerCode: 'Total Retailers',
                      totalQTY: 'Total Qty (K)',
                      totalRevenue: 'Total Value (Cr)'
                  };

                  return regions.map(region => {
                      const regionData = data.filter(item => item.region === region);
                      return {
                          name: `${metricLabels[metric]} - ${region}`,
                          group: group,
                          data: regionData.map(item => {
                              if (metric === 'totalQTY') return (item.totalQTY / 1000).toFixed(2); // Adjust units
                              if (metric === 'totalRevenue') return (item.totalRevenue / 10000000).toFixed(2) // Adjust units
                              return item.totalRetailerCode;
                          })
                      };
                  });
              };

              // Update chart options with dynamic series and categories
              this.RegionWiseMonthlyDistibutionOptions = {
                  ...this.RegionWiseMonthlyDistibutionOptions,
                  series: [
                      ...prepareSeriesData(response.body, 'totalRetailerCode', 'retailers'),
                      ...prepareSeriesData(response.body, 'totalQTY', 'qty'),
                      ...prepareSeriesData(response.body, 'totalRevenue', 'value')
                  ],
                  xaxis: { ...this.RegionWiseMonthlyDistibutionOptions.xaxis, categories },
                  title: {
                    text: 'Region Wise Distribution',
                    align: 'center',
                    // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
                },
                yaxis: [{
                  title: {
                      text: '(Quantity)',
                      style: { color: '#000000' }
                  },
                  labels: {
                      formatter: (val: any) => '' + val
                  },
                  tickAmount: 4
              }],
              };
          }
      },
      (error) => {
          this.spinner.hide();
          console.error('Error fetching region-wise monthly distribution data:', error);
      }
  );
};


  public RegionWiseGrowthOverPreviousMonth = (MonthlyToalOrdaringPayload?: any) => {
    this.spinner.show();

    const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    const categories: string[] = [];
    const isMobile = window.innerWidth <= 768;

    this.RegionWiseGrowthOverPreviousMonthOptions = {
        series: [
          { name: 'Retailers - East', group: 'retailers', data: [] },
          { name: 'Retailers - South1', group: 'retailers', data: [] },
          { name: 'Retailers - South2', group: 'retailers', data: [] },
          { name: 'Retailers - North', group: 'retailers', data: [] },
          { name: 'Retailers - West', group: 'retailers', data: [] },
    
          // Qty group
          { name: 'Qty - East', group: 'qty', data: [] },
          { name: 'Qty - South1', group: 'qty', data: [] },
          { name: 'Qty - South2', group: 'qty', data: [] },
          { name: 'Qty - North', group: 'qty', data: [] },
          { name: 'Qty - West', group: 'qty', data: [] },
    
          // Value group
          { name: 'Value - East', group: 'value', data: [] },
          { name: 'Value - South1', group: 'value', data: [] },
          { name: 'Value - South2', group: 'value', data: [] },
          { name: 'Value - North', group: 'value', data: [] },
          { name: 'Value - West', group: 'value', data: [] }
        ],
        title: {
            text: 'Region Wise Growth Loading...',
            align: 'center',
            // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
        },
        yaxis: [
            {
                title: {
                    text: '(Growth Value)',
                    style: { color: '#000000' }
                },
                labels: {
                  show:false,
                    formatter: (val: any) => '' + val
                },
                tickAmount: 4
            }
        ],
        xaxis: {
            labels: { trim: false ,show:false},
            categories: [],
            title: { style: { color: '#000000' } }
        },
        dataLabels: {
          //  enabled: true, 
           enabled: this.isMobileView ? false: true,

            style: {
          fontSize: "10px",
          colors: ['#000000'] 
        }, },
        chart: {
            type: 'bar',
            height: 500,
            stacked: true
        },
        plotOptions: { bar: { horizontal: false } },
        fill: { opacity: 1 },
        colors: [
            '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019',
            '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845',
            '#2ECC71', '#28B463', '#239B56', '#1D8348', '#186A3B'
        ],
        legend: { position: isMobile ? 'top' : 'right', horizontalAlign: 'left' }
    };

    this.dashboardService.RegionWiseGrowthOverPreviousMonth(MonthlyToalOrdaringPayload).subscribe(
        (response) => {
            if (response && response.body) {
                this.spinner.hide();

                // Prepare categories based on unique months
                const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
                uniqueMonths.forEach((month: any) => {
                    categories.push(monthNames[month - 1]);
                });

                // Function to prepare series data
                const prepareSeriesData = (data: any[], metric: string, group: string) => {
                    const regions = ['EAST', 'WEST', 'NORTH', 'SOUTH 1', 'SOUTH 2'];
                    const metricLabels: { [key: string]: string } = {
                        retailerGrowth: 'Retailer Growth',
                        orderGrowth: 'Order Growth (K)',
                        priceGrowth: 'Price Growth (Cr)'
                    };

                    return regions.map(region => {
                        const regionData = data.filter(item => item.region === region);
                        return {
                            name: `${metricLabels[metric]} - ${region}`,
                            group: group,
                            data: regionData.map(item => {
                                if (metric === 'orderGrowth') return (item.orderGrowth / 1000).toFixed(2); // Adjust units
                                if (metric === 'priceGrowth') return (item.priceGrowth / 10000000).toFixed(2); // Adjust units
                                return item.retailerGrowth;
                            })
                        };
                    });
                };

                // Update chart options with dynamic series and categories
                this.RegionWiseGrowthOverPreviousMonthOptions = {
                    ...this.RegionWiseGrowthOverPreviousMonthOptions,
                    series: [
                        ...prepareSeriesData(response.body, 'retailerGrowth', 'retailers'),
                        ...prepareSeriesData(response.body, 'orderGrowth', 'qty'),
                        ...prepareSeriesData(response.body, 'priceGrowth', 'value')
                    ],
                    xaxis: { ...this.RegionWiseGrowthOverPreviousMonthOptions.xaxis, categories },
                    title: {
                        text: 'Region Wise Growth',
                        align: 'center',
                        // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
                    },
                    yaxis: [{
                        title: {
                            text: '(Quantity)',
                            style: { color: '#000000' }
                        },
                        labels: {
                            formatter: (val: any) => '' + val
                        },
                        tickAmount: 4
                    }],
                };
            }
        },
        (error) => {
            console.error('Data loading error:', error);
        }
    );
};


// This is to get the master data based in the selection.
getMasterDataForFilter(data:any){
  let MonthlyToalOrdaringPayload: any = {};
    let abmNameList: any = '';
    let brandList: any = '';
    let rsNameList: any = '';
    let regionList: any = '';
    let retailerTypeList: any = '';
    let payloadForMaster:any;
    if (data === 'search') {
      // Prepare comma-separated strings for each array
      abmNameList = this.selectedAbmNames.map((item) => item.id).join(', ');
      brandList = this.selectedBrands.map((item) => item.name).join(', ');
      rsNameList = this.selectedRSNames.map((item) => item.name).join(', ');
      regionList = this.selectedRegions.map((item) => item.name).join(', ');
      retailerTypeList = this.selectedRetailerTypes.map((item) => item.name).join(', ');

      MonthlyToalOrdaringPayload = {
        regionList: regionList, /// default all regions
        startDate: 20240401, /// default case start date of financial year in integer format
        endDate: 202401030, ////  default case end date of financial year in integer format
        brandList: brandList, //// default casen ""
        rsNameList: rsNameList, //// default casen ""
        abmName:abmNameList,
        retailerType:retailerTypeList,
      };
      payloadForMaster = {
        "regionList": regionList,
        "abmName": abmNameList
        };
    } else {
        payloadForMaster = {
          "regionList": "",
          "abmName": ""
          };
    }
    this.GetMasterData(payloadForMaster,MonthlyToalOrdaringPayload,true);
}



// this is to get the monthly avg qty,value and orders.
public chartOptionslineForOrdBhFn = (MonthlyToalOrdaringPayload?: any) => {
  this.spinner.show();
        // Define month names for easy mapping
        const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

        // Prepare data for each series
        const retailersData :any= [];
        const quantityData :any= [];
        const valueData:any = [];
        const categories: string[] = []; // This will hold the month names

  this.chartOptionslineForOrdBh = {
    series: [
         {
        name: 'No Of Orders.',
        data: retailersData // Extracts totalRetailerCode values
      },
      {
        name: 'Avg Qty Per Order. (k)',
        data: quantityData // Extracts totalQTY values
      },
      {
        name: 'Avg Value Per Order. (Cr)',
        data: valueData // Extracts totalRevenue values
      }
    ],
    chart: {
      height: 500,
      type: 'line'
    },
    dataLabels: {
      enabled: false
    },
    stroke: {
      width: 5,
      curve: 'smooth', // Smooth curve for the line chart
      dashArray: [0, 8, 5] // Different dash arrays for each line
    },
    title: {
      text: 'Monthly Orders Trend Loading...',
      align: 'center'
    },
    // subtitle: {
    //   text: 'No of Re-retailers, Value, and Qty',
    //   align: 'center',
    //   style: {
    //     fontSize: '14px',
    //     color: '#666'
    //   }
    // },
    // legend: {
    //   tooltipHoverFormatter: function (val, opts) {
    //     return val + ' - <strong>' + opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] + '</strong>';
    //   }
    // },
    legend: {
      tooltipHoverFormatter: function (val, opts) {
        const dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
        const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
        return val + ' - <strong>' + formattedValue + '</strong>';
      }
    },            
    markers: {
      size: 5, // Size of markers on the line
      hover: {
        sizeOffset: 6
      }
    },
    xaxis: {
      labels: {
        trim: false,
          show:false,
      },
      categories: categories,
      title: {
        text: 'Monthly Orders Trend Loading...', // Left y-axis title
        style: {
          color: '#000000' // Change color as needed
        }
      },
    },
    yaxis: [
      {
        title: {
          text: 'Quantity (K) Loading...', // Left y-axis title
          style: {
            color: '#000000' // Change color as needed
          }
        },
        min: 0, // Minimum value for left y-axis
        labels: {
          show:false,
          formatter: function (val) {
            // return '' + val; // Format for value
            return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
          }
        },
        tickAmount: 4 // Adjust the number of ticks as necessary
      },
      // {
      //   opposite: false, // Make this y-axis on the opposite side
      //   title: {
      //     text: 'Value (Cr)', // Right y-axis title
      //     style: {
      //       color: '#000000' // Change color as needed
      //     }
      //   },
      //    labels: {
      //     formatter: function (val) {
      //       // return '' + val; // Format for value
      //       return val.toFixed(0);
      //     }
      //   },
      //   min: 0, // Minimum value for right y-axis
      //   tickAmount: 4 // Adjust the number of ticks as necessary
      // }
    ],
    tooltip: {
      shared: true, // Show shared tooltip
      intersect: false,
      y: [
        {
          title: {
            formatter: function (val) {
              return val;
            }
          }
        },
        {
          title: {
            formatter: function (val) {
              return val;
            }
          }
        },
        {
          title: {
            formatter: function (val) {
              return  val; // Format for value
            }
          }
        }
      ]
    }
  };
  this.dashboardService.chartOptionslineForOrdBh(MonthlyToalOrdaringPayload).subscribe(
    (response) => {
      if (response && response.body) {
        this.spinner.hide();
        this.RegionWiseMonthlyDistibutionOptionsFOrdBhFn(MonthlyToalOrdaringPayload);
        response.body.forEach((item: any) => {
          // Push values to each series array
          retailersData.push(item.noOforders/100);
          quantityData.push((item.avgQtyPerOrder));
          valueData.push((item.avgValuePerOrder / 1000));

          // Get month name and add to categories
          categories.push(monthNames[item.month - 1]);
        });
        // this.chartOptionslineForOrdBh = {
        //   series: [
        //     {
        //       name: 'No Of Orders.',
        //       data: retailersData // Extracts totalRetailerCode values
        //     },
        //     {
        //       name: 'Avg Qty Per Order. (k)',
        //       data: quantityData // Extracts totalQTY values
        //     },
        //     {
        //       name: 'Avg Value Per Order. (Cr)',
        //       data: valueData // Extracts totalRevenue values
        //     }
        //   ],
        //   chart: {
        //     height: 350,
        //     type: 'line'
        //   },
        //   dataLabels: {
        //     enabled: true
        //   },
        //   stroke: {
        //     width: 5,
        //     curve: 'smooth', // Smooth curve for the line chart
        //     dashArray: [0, 8, 5] // Different dash arrays for each line
        //   },
        //   title: {
        //     text: 'Monthly Orders Trend.',
        //     align: 'center'
        //   },
        //   // subtitle: {
        //   //   text: 'No of Re-retailers, Value, and Qty',
        //   //   align: 'center',
        //   //   style: {
        //   //     fontSize: '14px',
        //   //     color: '#666'
        //   //   }
        //   // },
        //   // legend: {
        //   //   tooltipHoverFormatter: function (val, opts) {
        //   //     return val + ' - <strong>' + opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] + '</strong>';
        //   //   }
        //   // },
        //   legend: {
        //     tooltipHoverFormatter: function (val, opts) {
        //       const dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
        //       const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
        //       return val + ' - <strong>' + formattedValue + '</strong>';
        //     }
        //   },            
        //   markers: {
        //     size: 5, // Size of markers on the line
        //     hover: {
        //       sizeOffset: 6
        //     }
        //   },
        //   xaxis: {
        //     labels: {
        //       trim: false
        //     },
        //     categories: categories
        //   },
        //   yaxis: [
        //     {
        //       title: {
        //         text: ' Avg Quantity Per Order (K)', // Left y-axis title
        //         style: {
        //           color: '#000000' // Change color as needed
        //         }
        //       },
        //       min: 0, // Minimum value for left y-axis
        //       labels: {
        //         formatter: function (val) {
        //           // return '' + val; // Format for value
        //           return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
        //         }
        //       },
        //       tickAmount: 4 // Adjust the number of ticks as necessary
        //     },
        //     {
        //       opposite: true, // Make this y-axis on the opposite side
        //       title: {
        //         text: 'Avg Value Per Order (Cr)', // Right y-axis title
        //         style: {
        //           color: '#000000' // Change color as needed
        //         }
        //       },
        //        labels: {
        //         formatter: function (val) {
        //           // return '' + val; // Format for value
        //           return val.toFixed(0);
        //         }
        //       },
        //       min: 0, // Minimum value for right y-axis
        //       tickAmount: 4 // Adjust the number of ticks as necessary
        //     }
        //   ],
        //   tooltip: {
        //     shared: true, // Show shared tooltip
        //     intersect: false,
        //     y: [
        //       {
        //         title: {
        //           formatter: function (val) {
        //             return val;
        //           }
        //         }
        //       },
        //       {
        //         title: {
        //           formatter: function (val) {
        //             return val;
        //           }
        //         }
        //       },
        //       {
        //         title: {
        //           formatter: function (val) {
        //             return  val; // Format for value
        //           }
        //         }
        //       }
        //     ]
        //   }
        // };
        this.chartOptionslineForOrdBh = {
          series: [
            {
              name: 'No Of Orders',
              data: retailersData // Extracts totalRetailerCode values
            },
            {
              name: 'Avg Qty Per Order',
              data: quantityData // Extracts totalQTY values
            },
            {
              name: 'Avg Value Per Order (k)',
              data: valueData // Extracts totalRevenue values
            }
          ],
          chart: {
            height: 500,
            type: 'line',
            zoom: {
              enabled: false
            },
          },
          dataLabels: {
            // enabled: true,
            enabled: this.isMobileView ? false: true,

            offsetX: -5,
            offsetY: -5,
            style: {
              fontSize: '10px',
            },
            background: {
              enabled: true,
              foreColor: '#000000'
            },
            formatter: function (val:any, { seriesIndex, w }) {
              const seriesName = w.config.series[seriesIndex].name;
              if (val === 'Avg Value Per Order (K)') {
                val = val * 1000;
             }  else if (seriesName === 'No Of Orders') {
                return (val * 100).toFixed(0); // Scale No Of Orders by 100
              }
              if (val === 'Avg Qty Per Order') {
                val = val.toFixed(0);
              }
                // Offset Y based on series index
          const dataLabelOffsetsY = [0,0,35];  // Customize offsets per series index
          const offsetY = dataLabelOffsetsY[seriesIndex] || 0; // Use the default offset if none provided
    
          // Update the offsetY dynamically
          w.config.dataLabels.offsetX = offsetY;
              return val.toFixed(2); // No scaling for Avg Value
            }
          },
          stroke: {
            width: 5,
            curve: 'smooth',
            dashArray: [0, 8, 5]
          },
          title: {
            text: 'Monthly Orders Trend',
            align: 'center'
          },
          legend: {
            tooltipHoverFormatter: function (val, opts) {
              let dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
              if (val === 'Avg Value Per Order (K)') {
                 dataValue = dataValue * 1000;
              } else if (val === 'No Of Orders') {
                dataValue = (dataValue * 100).toFixed(0);
              }
              const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
              if (val === 'Avg Qty Per Order') {
                dataValue = dataValue.toFixed(0);
              }
              return val + ' - <strong>' + formattedValue + '</strong>';
            }
          },
          markers: {
            size: 5,
            hover: {
              sizeOffset: 6
            }
          },
          xaxis: {
            labels: {
              trim: false
            },
            categories: categories
          },
          yaxis: {
            title: {
              text: 'Values',
              style: {
                color: '#000000'
              }
            },
            min: 0,
            labels: {
              formatter: function (val) {
                return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
              },
              offsetX: -20
            },
            tickAmount: 4
          },
          tooltip: {
            shared: true,
            intersect: false,
            y: {
              formatter: function (value:any, { series, seriesIndex }) {
                const seriesName = series[seriesIndex].name;
                if (value === 'Avg Value Per Order (k)') {
                  value = value * 1000;
               }  else if (seriesIndex === 0) {
                  return (value * 100).toFixed(0);
                }

                return value.toFixed(2);
              }
            }
          }
        };
        
        
      }
      console.log('this is for checking');
    },
    (error) => {
      // this.errorMessage = 'An error occurred during login';
      console.error('Login error:', error);
    }
  );
};


public RegionWiseMonthlyDistibutionOptionsFOrdBhFn = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  const categories: string[] = [];

  // Define chart options with placeholder series (to be updated dynamically)
  this.RegionWiseMonthlyDistibutionOptionsFOrdBh = {
    // series: [],
    series: [
      // Retailers group
      { name: 'No Of Orders - East', group: 'retailers', data: [] },
      { name: 'No Of Orders - South1', group: 'retailers', data: [] },
      { name: 'No Of Orders - South2', group: 'retailers', data: [] },
      { name: 'No Of Orders - North', group: 'retailers', data: [] },
      { name: 'No Of Orders - West', group: 'retailers', data: [] },

      // // Qty group
      // { name: 'Qty - East', group: 'qty', data: [] },
      // { name: 'Qty - South1', group: 'qty', data: [] },
      // { name: 'Qty - South2', group: 'qty', data: [] },
      // { name: 'Qty - North', group: 'qty', data: [] },
      // { name: 'Qty - West', group: 'qty', data: [] },

      // // Value group
      // { name: 'Value - East', group: 'value', data: [] },
      // { name: 'Value - South1', group: 'value', data: [] },
      // { name: 'Value - South2', group: 'value', data: [] },
      // { name: 'Value - North', group: 'value', data: [] },
      // { name: 'Value - West', group: 'value', data: [] }
    ],
    title: {
      text: 'Region Wise Monthly Distribution - No Of Orders Loading...',
      align: 'center',
      // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
    },
    yaxis: [
      {
        title: {
          text: '(No Of Orders) Loading...',
          style: { color: '#000000' }
        },
        labels: {
          show:false,
          formatter: (val: any) => '' + val
        },
        tickAmount: 4
      }
    ],
    xaxis: {
      labels: { trim: false ,  show:true,},
      categories: [],
      title: { style: { color: '#000000' } }
    },
    dataLabels: {
      //  enabled: false 
       enabled: this.isMobileView ? false: true,


    },
    chart: {
      type: 'bar',
      height: 500,
      stacked: true
    },
    plotOptions: { bar: { horizontal: false } },
    fill: { opacity: 1 },
    colors: [
      '#80c7fd',
      '#008FFB',
      '#80f1cb',
      '#00E396',
      '#feb019', // Retailers colors
      '#FF5733',
      '#FFBD33',
      '#C70039',
      '#900C3F',
      '#581845', // Qty colors
      '#2ECC71',
      '#28B463',
      '#239B56',
      '#1D8348',
      '#186A3B' // Value colors
    ],
    legend: { position: 'top', horizontalAlign: 'left' }
  };

  this.dashboardService.RegionWiseMonthlyDistibutionOptionsFOrdBhFn(MonthlyTotalOrderingPayload).subscribe(
      (response) => {
          if (response && response.body) {
              this.spinner.hide();
              this.RegionWiseMonthlyAvgPerOrderFn(MonthlyTotalOrderingPayload);
              // Prepare categories based on unique months
              const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
              uniqueMonths.forEach((month: any) => {
                  categories.push(monthNames[month - 1]);
              });

              // Prepare data series for each metric and region
              const prepareSeriesData = (data: any[], metric: string, group: string) => {
                  const regions = ['EAST', 'WEST', 'NORTH', 'SOUTH 1', 'SOUTH 2'];
                  const metricLabels: { [key: string]: string } = {
                      totalRetailerCode: 'No Of Orders %',
                      totalQTY: 'Total Qty (K)',
                      totalRevenue: 'Total Value (Cr)'
                  };

                  return regions.map(region => {
                      const regionData = data.filter(item => item.region === region);
                      return {
                          name: `${metricLabels[metric]} - ${region}`,
                          group: group,
                          data: regionData.map(item => {
                              if (metric === 'totalQTY') return (item.totalQTY / 10).toFixed(0); // Adjust units
                              if (metric === 'totalRevenue') return (item.totalRevenue / 10000000).toFixed(2) // Adjust units
                              return item.noOfOrdersPercentage.toFixed(2);
                          })
                      };
                  });
              };

              // Update chart options with dynamic series and categories
              this.RegionWiseMonthlyDistibutionOptionsFOrdBh = {
                  ...this.RegionWiseMonthlyDistibutionOptionsFOrdBh,
                  series: [
                      ...prepareSeriesData(response.body, 'totalRetailerCode', 'No Of Orders'),
                      // ...prepareSeriesData(response.body, 'totalQTY', 'qty'),
                      // ...prepareSeriesData(response.body, 'totalRevenue', 'value')
                  ],
                  xaxis: { ...this.RegionWiseMonthlyDistibutionOptionsFOrdBh.xaxis, categories },
                  title: {
                    text: 'Region Wise Monthly Distribution - No Of Orders.',
                    align: 'center',
                    // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
                },
                yaxis: [{
                  title: {
                      text: '(No Of Orders)',
                      style: { color: '#000000' }
                  },
                  labels: {
                      formatter: (val: any) => '' + val
                  },
                  tickAmount: 4
              }],
              };
          }
      },
      (error) => {
          this.spinner.hide();
          console.error('Error fetching region-wise monthly distribution data:', error);
      }
  );
};


// public RegionWiseMonthlyAvgPerOrderFn = (MonthlyTotalOrderingPayload?: any) => {
//   this.spinner.show();
//   const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
//   const categories: string[] = [];

//   // Define chart options with placeholder series (to be updated dynamically)
//   this.RegionWiseMonthlyAvgPerOrder = {
//     // series: [],
//     series: [
//       // Retailers group
//       { name: 'Avg Oty Per Order - East', group: 'retailers', data: [] },
//       { name: 'Avg Oty Per Order - South1', group: 'retailers', data: [] },
//       { name: 'Avg Oty Per Order - South2', group: 'retailers', data: [] },
//       { name: 'Avg Oty Per Order - North', group: 'retailers', data: [] },
//       { name: 'Avg Oty Per Order - West', group: 'retailers', data: [] },

//       // Qty group
//       { name: 'Avg Value Per Order - East', group: 'qty', data: [] },
//       { name: 'Avg Value Per Order - South1', group: 'qty', data: [] },
//       { name: 'Avg Value Per Order - South2', group: 'qty', data: [] },
//       { name: 'Avg Value Per Order - North', group: 'qty', data: [] },
//       { name: 'Avg Value Per Order - West', group: 'qty', data: [] },

//       // // Value group
//       // { name: 'Value - East', group: 'value', data: [] },
//       // { name: 'Value - South1', group: 'value', data: [] },
//       // { name: 'Value - South2', group: 'value', data: [] },
//       // { name: 'Value - North', group: 'value', data: [] },
//       // { name: 'Value - West', group: 'value', data: [] }
//     ],
//     title: {
//       text: 'Region Wise Monthly Avg Per Order Loading...',
//       align: 'center',
//       // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
//     },
//     yaxis: [
//       {
//         title: {
//           text: '(Avg Oty Per Order) Loading...',
//           style: { color: '#000000' }
//         },
//         labels: {
//           formatter: (val: any) => '' + val
//         },
//         tickAmount: 4
//       }
//     ],
//     xaxis: {
//       labels: { trim: false },
//       categories: [],
//       title: { style: { color: '#000000' } }
//     },
//     dataLabels: { enabled: true },
//     chart: {
//       type: 'bar',
//       height: 350,
//       stacked: true
//     },
//     plotOptions: { bar: { horizontal: false } },
//     fill: { opacity: 1 },
//     colors: [
//       '#80c7fd',
//       '#008FFB',
//       '#80f1cb',
//       '#00E396',
//       '#feb019', // Retailers colors
//       '#FF5733',
//       '#FFBD33',
//       '#C70039',
//       '#900C3F',
//       '#581845', // Qty colors
//       '#2ECC71',
//       '#28B463',
//       '#239B56',
//       '#1D8348',
//       '#186A3B' // Value colors
//     ],
//     legend: { position: 'top', horizontalAlign: 'left' }
//   };

//   this.dashboardService.RegionWiseMonthlyAvgPerOrderFn(MonthlyTotalOrderingPayload).subscribe(
//       (response) => {
//           if (response && response.body) {
//               this.spinner.hide();
//               // this.RegionWiseGrowthOverPreviousMonth(MonthlyTotalOrderingPayload);
//               // Prepare categories based on unique months
//               const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
//               uniqueMonths.forEach((month: any) => {
//                   categories.push(monthNames[month - 1]);
//               });

//               // Prepare data series for each metric and region
//               const prepareSeriesData = (data: any[], metric: string, group: string) => {
//                   const regions = ['EAST', 'WEST', 'NORTH', 'SOUTH 1', 'SOUTH 2'];
//                   const metricLabels: { [key: string]: string } = {
//                       totalRetailerCode: 'No Of Orders %',
//                       totalQTY: 'Avg Oty Per Order (K)',
//                       totalRevenue: 'Avg Value Per Order (Cr)'
//                   };

//                   return regions.map(region => {
//                       const regionData = data.filter(item => item.region === region);
//                       return {
//                           name: `${metricLabels[metric]} - ${region}`,
//                           group: group,
//                           data: regionData.map(item => {
//                               if (metric === 'avgQtyPerOrder') return (item.avgQtyPerOrder / 1000).toFixed(2); // Adjust units
//                               if (metric === 'avgPricePerOrder') return (item.avgPricePerOrder / 10000000).toFixed(2) // Adjust units
//                               return item.noOfOrdersPercentage;
//                           })
//                       };
//                   });
//               };

//               // Update chart options with dynamic series and categories
//               this.RegionWiseMonthlyAvgPerOrder = {
//                   ...this.RegionWiseMonthlyAvgPerOrder,
//                   series: [
//                       // ...prepareSeriesData(response.body, 'totalRetailerCode', 'No Of Orders'),
//                       ...prepareSeriesData(response.body, 'avgQtyPerOrder', 'qty'),
//                       ...prepareSeriesData(response.body, 'avgPricePerOrder', 'value')
//                   ],
//                   xaxis: { ...this.RegionWiseMonthlyAvgPerOrder.xaxis, categories },
//                   title: {
//                     text: 'Region Wise Monthly Avg Per Order.',
//                     align: 'center',
//                     // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
//                 },
//                 yaxis: [{
//                   title: {
//                       text: '(Avg Oty Per Order)',
//                       style: { color: '#000000' }
//                   },
//                   labels: {
//                       formatter: (val: any) => '' + val
//                   },
//                   tickAmount: 4
//               }],
//               };
//           }
//       },
//       (error) => {
//           this.spinner.hide();
//           console.error('Error fetching region-wise monthly distribution data:', error);
//       }
//   );
// };

public RegionWiseMonthlyAvgPerOrderFn = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  const categories: string[] = [];

  // Define chart options with placeholder series (to be updated dynamically)
  this.RegionWiseMonthlyAvgPerOrder = {
    series: [
      // Qty group
      { name: 'Avg Qty Per Order - East', group: 'qty', data: [] },
      { name: 'Avg Qty Per Order - South1', group: 'qty', data: [] },
      { name: 'Avg Qty Per Order - South2', group: 'qty', data: [] },
      { name: 'Avg Qty Per Order - North', group: 'qty', data: [] },
      { name: 'Avg Qty Per Order - West', group: 'qty', data: [] },

      // Value group
      { name: 'Avg Value Per Order - East', group: 'value', data: [] },
      { name: 'Avg Value Per Order - South1', group: 'value', data: [] },
      { name: 'Avg Value Per Order - South2', group: 'value', data: [] },
      { name: 'Avg Value Per Order - North', group: 'value', data: [] },
      { name: 'Avg Value Per Order - West', group: 'value', data: [] }
    ],
    title: {
      text: 'Region Wise Monthly Avg Per Order Loading...',
      align: 'center'
    },
    yaxis: [{
      title: {
        text: '(Avg Qty/Value Per Order)',
        style: { color: '#000000' }
      },
      labels: {
        show:false,
        formatter: (val: any) => '' + val
      },
      tickAmount: 4
    }],
    xaxis: {
      labels: { trim: false  , show:true,},
      categories: [],
      title: { style: { color: '#000000' } }
    },
    dataLabels: { 
      // enabled: false
      enabled: this.isMobileView ? false: true,

     },
    chart: { type: 'bar', height: 500, stacked: true },
    plotOptions: { bar: { horizontal: false } },
    fill: { opacity: 1 },
    colors: [
      '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019', // Qty colors
      '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845'  // Value colors
    ],
    legend: { position: 'right', horizontalAlign: 'left' }
  };

  this.dashboardService.RegionWiseMonthlyAvgPerOrderFn(MonthlyTotalOrderingPayload).subscribe(
    (response) => {
      if (response && response.body) {
        this.spinner.hide();

        // Prepare categories based on unique months
        const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
        uniqueMonths.forEach((month: any) => {
          categories.push(monthNames[month - 1]);
        });

        // Prepare data series for each metric and region
        const prepareSeriesData = (data: any[], metric: string, group: string) => {
          const regions = ['EAST', 'WEST', 'NORTH', 'SOUTH 1', 'SOUTH 2'];
          const metricLabels: { [key: string]: string } = {
            avgQtyPerOrder: 'Avg Qty Per Order',
            avgPricePerOrder: 'Avg Value Per Order (K)'
          };

          return regions.map(region => {
            const regionData = data.filter(item => item.region === region);
            return {
              name: `${metricLabels[metric]} - ${region}`,
              group: group,
              data: regionData.map(item => {
                if (metric === 'avgQtyPerOrder') return (item.avgQtyPerOrder).toFixed(2); // Adjust units
                if (metric === 'avgPricePerOrder') return (item.avgPricePerOrder / 1000).toFixed(2); // Adjust units
                return item.noOfOrdersPercentage;
              })
            };
          });
        };

        // Update chart options with dynamic series and categories
        this.RegionWiseMonthlyAvgPerOrder = {
          ...this.RegionWiseMonthlyAvgPerOrder,
          series: [
            ...prepareSeriesData(response.body, 'avgQtyPerOrder', 'qty'),
            ...prepareSeriesData(response.body, 'avgPricePerOrder', 'value')
          ],
          xaxis: { ...this.RegionWiseMonthlyAvgPerOrder.xaxis, categories },
          title: { text: 'Region Wise Monthly Avg Per Order', align: 'center' },
          yaxis: [{
            title: {
              text: '(Avg Qty/Value Per Order)',
              style: { color: '#000000' }
            },
            labels: { formatter: (val: any) => '' + val },
            tickAmount: 4
          }]
        };
      }
    },
    (error) => {
      this.spinner.hide();
      console.error('Error fetching region-wise monthly avg per order data:', error);
    }
  ); 
};

toggleDropdownVisibilityForMonth() {
  this.toggleDropdownVisibility1('isDropdownOpenForMonth');
    if (this.isDropdownOpenForMonth) {
      this.filteredMonthsList = this.availableMonths; // Reset filtered list on opening
    }
  // this.isDropdownOpenForMonth = !this.isDropdownOpenForMonth;
}

toggleMonthSelection(month: any) {
  if (this.selectedMonths.includes(month)) {
    this.selectedMonths = this.selectedMonths.filter(m => m.id !== month.id);
  } else {
    this.selectedMonths.push(month);
  }
  this.areAllMonthsSelected = this.selectedMonths.length === this.availableMonths.length;
}

removeSelectedMonth(month: any, event: Event) {
  event.stopPropagation();
  this.selectedMonths = this.selectedMonths.filter(m => m.id !== month.id);
  this.areAllMonthsSelected = this.selectedMonths.length === this.availableMonths.length;
}

toggleAllMonthSelection(event: any) {
  if (event.target.checked) {
    this.selectedMonths = [...this.availableMonths];
    this.areAllMonthsSelected = true;
  } else {
    this.selectedMonths = [];
    this.areAllMonthsSelected = false;
  }
}

filterAvailableMonths() {
  const searchValue = this.searchInputValueForMonth.toLowerCase();
  this.filteredMonthsList = this.availableMonths.filter(month =>
    month.name.toLowerCase().includes(searchValue)
  );
}

resetChartOptions() {
  this.chartOptionsline = {};
  this.chartOptionsRegionwise = {};
  this.chartOptionsRegionwiseGrowth = {};
  this.regionWiseGrowthOptions = {};
  this.budgetChartOptions = {};
  this.RegionWiseMonthlyDistibutionOptions = {};
  this.RegionWiseGrowthOverPreviousMonthOptions = {};
  this.chartOptions = {};
  // Menu id 3 specific charts
  this.chartOptionslineForOrdBh = {};
  this.RegionWiseMonthlyDistibutionOptionsFOrdBh = {};
  this.RegionWiseMonthlyAvgPerOrder = {};
}

// orders by day of the month.

// // this is to get the monthly avg qty,value and orders.
// public percentageOfOrdersOfDayInMonth = (MonthlyToalOrdaringPayload?: any) => {
//   this.spinner.show();
//         // Define month names for easy mapping
//         const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

//         // Prepare data for each series
//         const retailersData :any= [];
//         const quantityData :any= [];
//         const valueData:any = [];
//         const categories: string[] = []; // This will hold the month names

//   this.percentageOfOrdersOfDayInMonthOptions = {
//     series: [
//          {
//         name: 'No Of Orders.',
//         data: retailersData // Extracts totalRetailerCode values
//       },
//       {
//         name: 'Avg Qty Per Order. (k)',
//         data: quantityData // Extracts totalQTY values
//       },
//       {
//         name: 'Avg Value Per Order. (Cr)',
//         data: valueData // Extracts totalRevenue values
//       }
//     ],
//     chart: {
//       height: 500,
//       type: 'line'
//     },
//     dataLabels: {
//       enabled: false
//     },
//     stroke: {
//       width: 5,
//       curve: 'smooth', // Smooth curve for the line chart
//       dashArray: [0, 8, 5] // Different dash arrays for each line
//     },
//     title: {
//       text: 'Monthly Orders Trend Loading...',
//       align: 'center'
//     },
//     legend: {
//       tooltipHoverFormatter: function (val, opts) {
//         const dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
//         const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
//         return val + ' - <strong>' + formattedValue + '</strong>';
//       }
//     },            
//     markers: {
//       size: 5, // Size of markers on the line
//       hover: {
//         sizeOffset: 6
//       }
//     },
//     xaxis: {
//       labels: {
//         trim: false,
//           show:false,
//       },
//       categories: categories,
//       title: {
//         text: 'Monthly Orders Trend Loading...', // Left y-axis title
//         style: {
//           color: '#000000' // Change color as needed
//         }
//       },
//     },
//     yaxis: [
//       {
//         title: {
//           text: 'Quantity (K) Loading...', // Left y-axis title
//           style: {
//             color: '#000000' // Change color as needed
//           }
//         },
//         min: 0, // Minimum value for left y-axis
//         labels: {
//           show:false,
//           formatter: function (val) {
//             // return '' + val; // Format for value
//             return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
//           }
//         },
//         tickAmount: 4 // Adjust the number of ticks as necessary
//       },
      
//     ],
//     tooltip: {
//       shared: true, // Show shared tooltip
//       intersect: false,
//       y: [
//         {
//           title: {
//             formatter: function (val) {
//               return val;
//             }
//           }
//         },
//         {
//           title: {
//             formatter: function (val) {
//               return val;
//             }
//           }
//         },
//         {
//           title: {
//             formatter: function (val) {
//               return  val; // Format for value
//             }
//           }
//         }
//       ]
//     }
//   };
//   this.dashboardService.percentageOfOrdersOfDayInMonthFn(MonthlyToalOrdaringPayload).subscribe(
//     (response) => {
//       if (response && response.body) {
//         this.spinner.hide();
//         // this.RegionWiseMonthlyDistibutionOptionsFOrdBhFn(MonthlyToalOrdaringPayload);
//         response.body.forEach((item: any) => {
//           // Push values to each series array
//           retailersData.push(item.noOforders/100);
//           quantityData.push((item.avgQtyPerOrder));
//           valueData.push((item.avgValuePerOrder / 1000));

//           // Get month name and add to categories
//           categories.push(monthNames[item.month - 1]);
//         });
       
//         this.percentageOfOrdersOfDayInMonthOptions = {
//           series: [
//             {
//               name: 'No Of Orders',
//               data: retailersData // Extracts totalRetailerCode values
//             },
//             {
//               name: 'Avg Qty Per Order',
//               data: quantityData // Extracts totalQTY values
//             },
//             {
//               name: 'Avg Value Per Order (k)',
//               data: valueData // Extracts totalRevenue values
//             }
//           ],
//           chart: {
//             height: 500,
//             type: 'line',
//             zoom: {
//               enabled: false
//             },
//           },
//           dataLabels: {
//             // enabled: true,
//             enabled: this.isMobileView ? false: true,

//             offsetX: -5,
//             offsetY: -5,
//             style: {
//               fontSize: '10px',
//             },
//             background: {
//               enabled: true,
//               foreColor: '#000000'
//             },
//             formatter: function (val:any, { seriesIndex, w }) {
//               const seriesName = w.config.series[seriesIndex].name;
//               if (val === 'Avg Value Per Order (K)') {
//                 val = val * 1000;
//              }  else if (seriesName === 'No Of Orders') {
//                 return (val * 100).toFixed(0); // Scale No Of Orders by 100
//               }
//               if (val === 'Avg Qty Per Order') {
//                 val = val.toFixed(0);
//               }
//                 // Offset Y based on series index
//           const dataLabelOffsetsY = [0,0,35];  // Customize offsets per series index
//           const offsetY = dataLabelOffsetsY[seriesIndex] || 0; // Use the default offset if none provided
    
//           // Update the offsetY dynamically
//           w.config.dataLabels.offsetX = offsetY;
//               return val.toFixed(2); // No scaling for Avg Value
//             }
//           },
//           stroke: {
//             width: 5,
//             curve: 'smooth',
//             dashArray: [0, 8, 5]
//           },
//           title: {
//             text: 'Monthly Orders Trend',
//             align: 'center'
//           },
//           legend: {
//             tooltipHoverFormatter: function (val, opts) {
//               let dataValue = opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex];
//               if (val === 'Avg Value Per Order (K)') {
//                  dataValue = dataValue * 1000;
//               } else if (val === 'No Of Orders') {
//                 dataValue = (dataValue * 100).toFixed(0);
//               }
//               const formattedValue = dataValue % 1 === 0 ? dataValue : dataValue.toFixed(2);
//               if (val === 'Avg Qty Per Order') {
//                 dataValue = dataValue.toFixed(0);
//               }
//               return val + ' - <strong>' + formattedValue + '</strong>';
//             }
//           },
//           markers: {
//             size: 5,
//             hover: {
//               sizeOffset: 6
//             }
//           },
//           xaxis: {
//             labels: {
//               trim: false
//             },
//             categories: categories
//           },
//           yaxis: {
//             title: {
//               text: 'Values',
//               style: {
//                 color: '#000000'
//               }
//             },
//             min: 0,
//             labels: {
//               formatter: function (val) {
//                 return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
//               },
//               offsetX: -20
//             },
//             tickAmount: 4
//           },
//           tooltip: {
//             shared: true,
//             intersect: false,
//             y: {
//               formatter: function (value:any, { series, seriesIndex }) {
//                 const seriesName = series[seriesIndex].name;
//                 if (value === 'Avg Value Per Order (k)') {
//                   value = value * 1000;
//                }  else if (seriesIndex === 0) {
//                   return (value * 100).toFixed(0);
//                 }

//                 return value.toFixed(2);
//               }
//             }
//           }
//         };
        
        
//       }
//       console.log('this is for checking');
//     },
//     (error) => {
//       // this.errorMessage = 'An error occurred during login';
//       console.error('Login error:', error);
//     }
//   );
// };

public percentageOfOrdersOfDayInMonth = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();

  // Prepare data for the series
  const percentageData: any = [];
  const orderCounts: any = [];
  const daysInMonth: string[] = Array.from({ length: 31 }, (_, i) => (i + 1).toString());

  // Initial chart options with loading text in title
  this.percentageOfOrdersOfDayInMonthOptions = {
    series: [
      {
        name: 'Order Percentage',
        data: percentageData
      }
    ],
    chart: {
      height: 500,
      type: 'line',
      zoom :{
        enabled:false
      }
    },
    dataLabels: {
      enabled: true, // Enable data labels
      formatter: (val:any) => `${val.toFixed(0)}%` // Format as percentage
    },
    stroke: {
      width: 2,
      curve: 'smooth'
    },
    title: {
      text: '% Of Orders By Day Of The Month Loading...', // Initial title with loading text
      align: 'center'
    },
    xaxis: {
      categories: daysInMonth,
      title: {
        text: 'Day of Month',
        style: {
          color: '#000000'
        }
      },
    },
    yaxis: {
      title: {
        text: 'Order Percentage',
        style: {
          color: '#000000'
        }
      },
      labels: {
        formatter: (val) => val.toFixed(0) + '%' // Format as percentage
      },
      min: 0
    },
    // tooltip: {
    //   shared: true,
    //   intersect: false,
    //   y: {
    //     formatter: function (val, { dataPointIndex }) {
    //       // Display both percentage and number of orders in the tooltip
    //       const orders = orderCounts[dataPointIndex] || 0;
    //       return `Order %: ${val.toFixed(2)}% <br>Orders: ${orders}`;
    //     }
    //   }
    // },
    tooltip: {
      shared: true,
      intersect: false,
      custom: ({ series, seriesIndex, dataPointIndex, w }) => {
        // Access percentage value and number of orders for the tooltip
        const percentage = series[seriesIndex][dataPointIndex];
        const orders = orderCounts[dataPointIndex] || 0;
    
        // Custom tooltip content with percentage and order count
        return `
          <div style="padding: 10px; border-radius: 5px; background-color: #f4f4f4; color: #333;">
            <strong>Day: ${daysInMonth[dataPointIndex]}</strong><br>
            Order %: ${percentage.toFixed(2)}<br>
            Orders: ${orders}
          </div>
        `;
      }
    },
    markers: {
      size: 5,
      hover: {
        sizeOffset: 6
      }
    },
    legend: {
      position: 'top'
    }
  };

  this.dashboardService.percentageOfOrdersOfDayInMonthFn(MonthlyTotalOrderingPayload).subscribe(
    (response) => {
      if (response && response.body) {
        this.spinner.hide();
        this.percentageofOrdersbyWeekdayorWeekendFN(MonthlyTotalOrderingPayload);
        response.body.forEach((item: any) => {
          // Populate data for each day
          percentageData.push(item.percentageOfOrders); // Add percentage data
          orderCounts.push(item.distinctOrderCount); // Store order count for tooltip
        });

        // Update chart options with fetched data and remove loading from title
        this.percentageOfOrdersOfDayInMonthOptions = {
          ...this.percentageOfOrdersOfDayInMonthOptions,
          title: {
            text: '% Of Orders By Day Of The Month', // Update title after data load
            align: 'center'
          },
          series: [
            {
              name: 'Order Percentage',
              data: percentageData
            }
          ]
        };
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};


public percentageofOrdersbyWeekdayorWeekendFN = (MonthlyTotalOrderingPayload?: any) => {
  // Default payload to previous month if none is provided
  MonthlyTotalOrderingPayload = MonthlyTotalOrderingPayload || {
    startDate: '20241001',
    endDate: '20241031'
  };

  // Show spinner and set loading title
  this.spinner.show();
  this.percentageofOrdersbyWeekdayorWeekendOptionsPiechart = {
    ...this.percentageofOrdersbyWeekdayorWeekendOptionsPiechart,
    title: { text: 'Loading data...', align: 'center' }
  };
  this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart = {
    ...this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart,
    title: { text: 'Loading data...', align: 'center' }
  };

  // Prepare data for the charts
  const pieSeriesData: number[] = [];   // For percentage data for Weekday and Weekend
  const pieLabels: string[] = [];       // For labeling the pie chart slices
  const barCategories: string[] = ['Weekday', 'Weekend'];
  const barSeriesData: number[] = [];   // For order count for Weekday and Weekend
  const distinctOrderCounts: number[] = []; // To store distinct order counts for tooltip

  // Define Pie Chart options
  this.percentageofOrdersbyWeekdayorWeekendOptionsPiechart = {
    series: [],
    chart: {
      type: 'pie',
      height: 500
    },
    labels: [], // Set labels dynamically based on data received
    title: {
      text: 'Percentage of Orders by Weekday/Weekend',
      align: 'center'
    },
    legend: {
      position: 'bottom'
    },
    tooltip: {
      y: {
        formatter: (val: any) => `${val.toFixed(2)}%`
      }
    }
  };

  // Define Bar Chart options
  this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart = {
    series: [
      {
        name: 'Percentage of Orders',
        data: barSeriesData // We will only show percentage here
      }
    ],
    chart: {
      height: 500,
      type: 'bar'
    },
    dataLabels: {
      enabled: true,
      formatter: (val: any) => `${val.toFixed(2)}%`
    },
    title: {
      text: 'Order Count by Weekday/Weekend',
      align: 'center'
    },
    xaxis: {
      categories: barCategories,
      title: {
        text: 'Day Type'
      }
    },
    yaxis: {
      title: {
        text: 'Percentage of Orders'
      },
      labels: {
        formatter: (val: any) => `${val.toFixed(2)}%`
      }
    },
    tooltip: {
      shared: true,
      intersect: false,
      custom: ({ series, seriesIndex, dataPointIndex, w }) => {
        const distinctOrderCount = distinctOrderCounts[dataPointIndex] || 0;
        return `
          <div style="padding: 10px; border-radius: 5px; background-color: #f4f4f4; color: #333;">
            <strong>${barCategories[dataPointIndex]}</strong><br>
            Order %: ${series[seriesIndex][dataPointIndex].toFixed(2)}%<br>
            Orders: ${distinctOrderCount}
          </div>
        `;
      }
    },
    markers: {
      size: 5
    },
    legend: {
      position: 'top'
    }
  };

  // Fetch data
  this.dashboardService.percentageofOrdersbyWeekdayorWeekendFn(MonthlyTotalOrderingPayload).subscribe(
    (response) => {
      if (response && response.body) {
        this.spinner.hide();
this.percentageofOrdersByWeekdayorWeekendRegionWiseFn(MonthlyTotalOrderingPayload);
        response.body.forEach((item: any) => {
          // Populate pie chart data
          pieSeriesData.push(item.percentageOfOrders);
          pieLabels.push(item.dayType); // Set labels from API response ("Weekday" or "Weekend")

          // Populate bar chart data with percentages and distinct order counts for tooltip
          barSeriesData.push(item.percentageOfOrders);
          distinctOrderCounts.push(item.distinctOrderCount);
        });

        // Update pie chart with actual data and labels
        this.percentageofOrdersbyWeekdayorWeekendOptionsPiechart = {
          ...this.percentageofOrdersbyWeekdayorWeekendOptionsPiechart,
          series: pieSeriesData,
          labels: pieLabels, // Assign dynamically set labels here
          title: {
            text: 'Percentage of Orders by Weekday/Weekend',
            align: 'center'
          }
        };

        // Update bar chart with actual data and remove loading title
        this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart = {
          ...this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart,
          series: [
            {
              name: 'Percentage of Orders',
              data: barSeriesData
            }
          ],
          title: {
            text: 'Order Count by Weekday/Weekend',
            align: 'center'
          }
        };
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};

public percentageofOrdersByWeekdayorWeekendRegionWiseFn = (MonthlyTotalOrderingPayload?: any) => {
  // Default payload to previous month if none is provided
  MonthlyTotalOrderingPayload = MonthlyTotalOrderingPayload || {
    startDate: '20241001',
    endDate: '20241031'
  };

  // Show spinner and set loading title
  this.spinner.show();
  this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart = {
    ...this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart,
    title: { text: 'Loading data...', align: 'center' }
  };

  // Prepare data for the grouped bar chart
  const barCategories: string[] = [];   // For x-axis categories (regions)
  const weekdayData: number[] = [];     // For Weekday data
  const weekendData: number[] = [];     // For Weekend data
  const distinctOrderCountsWeekday: number[] = []; // To store distinct order counts for Weekday tooltip
  const distinctOrderCountsWeekend: number[] = []; // To store distinct order counts for Weekend tooltip

  // Define Bar Chart options
  this.percentageofOrdersByWeekdayorWeekendRegionWiseOptions = {
    series: [
      {
        name: 'Weekday',
        data: weekdayData
      },
      {
        name: 'Weekend',
        data: weekendData
      }
    ],
    chart: {
      height: 500,
      type: 'bar',
      stacked: false // Keep bars separate (non-stacked)
    },
    dataLabels: {
      enabled: true,
      formatter: (val: any) => `${val.toFixed(2)}%` // Display percentage
    },
    title: {
      text: 'Order Count by Region (Weekday vs Weekend)',
      align: 'center'
    },
    xaxis: {
      categories: barCategories,
      title: {
        text: 'Region Wise'
      }
    },
    yaxis: {
      title: {
        text: 'Percentage of Orders'
      },
      labels: {
        formatter: (val: any) => `${val.toFixed(2)}%`
      }
    },
    tooltip: {
      shared: true,
      intersect: false,
      custom: ({ series, seriesIndex, dataPointIndex, w }) => {
        const distinctOrderCount = seriesIndex === 0 
          ? distinctOrderCountsWeekday[dataPointIndex] // Weekday
          : distinctOrderCountsWeekend[dataPointIndex]; // Weekend

        return `
          <div style="padding: 10px; border-radius: 5px; background-color: #f4f4f4; color: #333;">
            <strong>${barCategories[dataPointIndex]}</strong><br>
            ${series[seriesIndex][dataPointIndex].toFixed(2)}%<br>
            Orders: ${distinctOrderCount}
          </div>
        `;
      }
    },
    markers: {
      size: 5
    },
    legend: {
      position: 'top'
    }
  };

  // Fetch data
  this.dashboardService.percentageofOrdersByWeekdayorWeekendRegionWiseFn(MonthlyTotalOrderingPayload).subscribe(
    (response) => {
      if (response && response.body) {
        this.spinner.hide();
this.percentaageOfOrdersByHourOfTheDayOnWeekdayWeekendFn(MonthlyTotalOrderingPayload);
        const groupedData = response.body.reduce((acc:any, item:any) => {
          const region = item.region;
          const dayType = item.dayType;
          
          // Ensure region exists in the accumulator
          if (!acc[region]) {
            acc[region] = { weekday: 0, weekend: 0, weekdayOrders: 0, weekendOrders: 0 };
          }

          if (dayType === 'Weekday') {
            acc[region].weekday = item.percentageOfOrders;
            acc[region].weekdayOrders = item.distinctOrderCount;
          } else if (dayType === 'Weekend') {
            acc[region].weekend = item.percentageOfOrders;
            acc[region].weekendOrders = item.distinctOrderCount;
          }

          return acc;
        }, {});

        // Now populate chart data
        for (const region in groupedData) {
          if (groupedData.hasOwnProperty(region)) {
            barCategories.push(region);
            weekdayData.push(groupedData[region].weekday);
            weekendData.push(groupedData[region].weekend);
            distinctOrderCountsWeekday.push(groupedData[region].weekdayOrders);
            distinctOrderCountsWeekend.push(groupedData[region].weekendOrders);
          }
        }

        // Update bar chart with actual data and remove loading title
        this.percentageofOrdersByWeekdayorWeekendRegionWiseOptions = {
          ...this.percentageofOrdersByWeekdayorWeekendRegionWiseOptions,
          series: [
            { name: 'Weekday', data: weekdayData },
            { name: 'Weekend', data: weekendData }
          ],
          title: {
            text: 'Order Count by Region (Weekday vs Weekend)',
            align: 'center'
          }
        };
      }
    },
    (error) => {
      console.error('Error fetching order data:', error);
      this.spinner.hide();
    }
  );
};

public percentaageOfOrdersByHourOfTheDayOnWeekdayWeekendFn = (MonthlyTotalOrderingPayload?: any) => {
  MonthlyTotalOrderingPayload = MonthlyTotalOrderingPayload || {
    startDate: '20241001',
    endDate: '20241031'
  };

  this.spinner.show();
  this.percentageofOrdersByHourOfTheDayOptionsBarchart = {
    ...this.percentageofOrdersByHourOfTheDayOptionsBarchart,
    title: { text: 'Loading data...', align: 'center' }
  };

  // Prepare data for the grouped bar chart
  const hours = Array.from({ length: 24 }, (_, i) => i.toString()); // x-axis categories: 0-23 hours
  const weekdayData: number[] = Array(24).fill(0); // Initialize with 0 values for all hours
  const weekendData: number[] = Array(24).fill(0);
  const weekdayOrderCounts: number[] = Array(24).fill(0); // Store order counts for tooltips
  const weekendOrderCounts: number[] = Array(24).fill(0);

  // Define Bar Chart options
  this.percentageofOrdersByHourOfTheDayOptionsBarchart = {
    series: [
      {
        name: 'Weekday',
        data: weekdayData
      },
      {
        name: 'Weekend',
        data: weekendData
      }
    ],
    chart: {
      height: 500,
      type: 'bar',
      stacked: false
    },
    dataLabels: {
      enabled: false,
      formatter: (val: any) => `${val.toFixed(2)}%`,
      offsetY: 25, 
      style: {
        fontSize: '10px',
      },
      background: {
        enabled: true,
        foreColor: '#000000'
      },
    },
    title: {
      text: 'Order Percentage by Hour (Weekday vs Weekend)',
      align: 'center'
    },
    xaxis: {
      categories: hours,
      title: {
        text: 'Hour of the Day'
      }
    }, plotOptions: {
      bar: {
        horizontal: false,
        columnWidth: '80%'
        // endingShape: "rounded"
      }
    },
    yaxis: {
      title: {
        text: 'Percentage of Orders'
      },
      labels: {
        formatter: (val: any) => `${val.toFixed(2)}%`
      }
      
    },
    // tooltip: {
    //   shared: true,
    //   intersect: false,
    //   custom: ({ series, seriesIndex, dataPointIndex, w }) => {
    //     const isWeekday = seriesIndex === 0;
    //     const distinctOrderCount = isWeekday 
    //       ? weekdayOrderCounts[dataPointIndex]
    //       : weekendOrderCounts[dataPointIndex];
    //     const percentage = series[seriesIndex][dataPointIndex];

    //     return `
    //       <div style="padding: 10px; border-radius: 5px; background-color: #f4f4f4; color: #333;">
    //         <strong>${hours[dataPointIndex]}:00</strong><br>
    //         ${isWeekday ? 'Weekday' : 'Weekend'}<br>
    //         ${percentage.toFixed(2)}%<br>
    //         Orders: ${distinctOrderCount}
    //       </div>
    //     `;
    //   }
    // },
    tooltip: {
      shared: true,
      intersect: false,
      custom: ({ dataPointIndex, w }) => {
        // Retrieve weekday and weekend data for the current hour (dataPointIndex)
        const weekdayPercentage = w.config.series[0].data[dataPointIndex];
        const weekendPercentage = w.config.series[1].data[dataPointIndex];
        
        const weekdayOrders = weekdayOrderCounts[dataPointIndex];
        const weekendOrders = weekendOrderCounts[dataPointIndex];
    
        return `
          <div style="padding: 10px; border-radius: 5px; background-color: #f4f4f4; color: #333;">
            <strong>${hours[dataPointIndex]}:00</strong><br>
            <div>Weekday</div>
            <div>Percentage: ${weekdayPercentage.toFixed(2)}%</div>
            <div>Orders: ${weekdayOrders}</div>
            <br>
            <div>Weekend</div>
            <div>Percentage: ${weekendPercentage.toFixed(2)}%</div>
            <div>Orders: ${weekendOrders}</div>
          </div>
        `;
      }
    },
    legend: {
      position: 'top'
    }
  };

  // Fetch data
  this.dashboardService.percentaageOfOrdersByHourOfTheDayOnWeekdayWeekendFn(MonthlyTotalOrderingPayload).subscribe(
    (response) => {
      if (response && response.body) {
        this.spinner.hide();

        // Group data by hour and day type
        response.body.forEach((item: any) => {
          const hour = item.orderHour;
          const isWeekday = item.dayType === 'Weekday';

          if (isWeekday) {
            weekdayData[hour] = item.percentageOfOrders;
            weekdayOrderCounts[hour] = item.distinctOrderCount;
          } else {
            weekendData[hour] = item.percentageOfOrders;
            weekendOrderCounts[hour] = item.distinctOrderCount;
          }
        });

        // Check if weekend data is present and update the chart title
        const weekendDataAvailable = weekendData.some((val) => val > 0);

        // Update chart options with actual data and remove loading title
        this.percentageofOrdersByHourOfTheDayOptionsBarchart = {
          ...this.percentageofOrdersByHourOfTheDayOptionsBarchart,
          series: [
            { name: 'Weekday', data: weekdayData },
            { name: 'Weekend', data: weekendData }
          ],
          title: {
            text: weekendDataAvailable 
              ? 'Order Percentage by Hour (Weekday vs Weekend)' 
              : 'Order Percentage by Hour (Weekday)',
            align: 'center'
          }
        };
      }
    },
    (error) => {
      console.error('Error fetching order data:', error);
      this.spinner.hide();
    }
  );
};


}
