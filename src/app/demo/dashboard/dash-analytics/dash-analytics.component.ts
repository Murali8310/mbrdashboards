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
type DropdownFlag = 'isDropdownOpenForRetailer' | 'isDropdownOpenForRS' | 'isDropdownOpenForBrand' | 'isDropdownOpen' | 'isDropdownOpenForAbm';

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
  public appendClass: any;


  @HostListener('window:click', ['$event'])
  onWindowClick(event: MouseEvent) {
    // Ignore clicks on the specific button with id 'allowClickButton'
    const targetElement = event.target as HTMLElement;

    if (
      targetElement.closest('#allowClickButton') || targetElement.closest('#allowClickButtonabm') || targetElement.closest('#restrictclicksabm')||
      targetElement.closest('#restrictclicks') || targetElement.closest('#retailerTypeallow') || targetElement.closest('#retailerTypeallow') || targetElement.closest('#retailerTypeallowDropdown')
      ||targetElement.closest('#rsnamemenu2')||targetElement.closest('#rsnamemenu') || targetElement.closest('#brandDropdown') ||targetElement.closest('#brandDropdownmenu')
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
    // { id: 1, name: 'East' },
    // { id: 2, name: 'North' },
    // { id: 3, name: 'South1' },
    // { id: 4, name: 'South2' },
    // { id: 5, name: 'West' }
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

  // Holds selected brands
  selectedBrands: { id: number; name: string }[] = [];

  // Control dropdown visibility
  isDropdownOpenForBrand = false;

  // Search input value
  searchInputValueForBrand = '';

  // Filtered brands based on search input
  filteredBrandsList = this.availableBrands;
  isLoading = false; // Start with loading state

  // constructor
  constructor(
    public dashboardService: DashboardService,
    private spinner: NgxSpinnerService,
    private router: Router,
    private route: ActivatedRoute
  ) {

    this.regionWiseGrowthOptions = {
      series: [
        // Retailers group for 5 regions
        {
          name: "East Region",
          group: "retailers",
          data: [44000, 55000, 41000, 67000, 22000, 43000]
        },
        {
          name: "North Region",
          group: "retailers",
          data: [13000, 36000, 20000, 8000, 13000, 27000]
        },
        {
          name: "South 1 Region",
          group: "retailers",
          data: [13000, 36000, 20000, 8000, 13000, 27000]
        },
        {
          name: "South 2 Region",
          group: "retailers",
          data: [13000, 36000, 20000, 8000, 13000, 27000]
        },
        {
          name: "West Region",
          group: "retailers",
          data: [13000, 36000, 20000, 8000, 13000, 27000]
        },
    
        // qty group for same 5 regions
        {
          name: "qty East Region",
          group: "qty",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
        {
          name: "qty North Region",
          group: "qty",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
        {
          name: "qty South 1 Region",
          group: "qty",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
        {
          name: "qty South 2 Region",
          group: "qty",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
        {
          name: "qty West Region",
          group: "qty",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
    
        // value group for same 5 regions
        {
          name: "Value East Region",
          group: "value",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
        {
          name: "Value North Region",
          group: "value",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
        {
          name: "Value South 1 Region",
          group: "value",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
        {
          name: "Value South 2 Region",
          group: "value",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        },
        {
          name: "Value West Region",
          group: "value",
          data: [20000, 40000, 25000, 10000, 12000, 28000]
        }
      ],
      chart: {
        type: "bar",
        height: 350,
        stacked: true
      },
      stroke: {
        width: 1,
        colors: ["#fff"]
      },
      dataLabels: {
        formatter: (val) => {
          return Number(val) / 1000 + "K";
        }
      },
      plotOptions: {
        bar: {
          horizontal: false
        }
      },
      xaxis: {
        categories: [
          'April',
          'May',
          'June'
        ]
      },
      fill: {
        opacity: 1
      },
      colors: ["#80c7fd", "#008FFB", "#80f1cb", "#00E396"],
      yaxis: {
        labels: {
          formatter: (val) => {
            return val / 1000 + "K";
          }
        }
      },
      legend: {
        position: "top",
        horizontalAlign: "left"
      }
    };
    
    // const queryParams = (router.routerState.snapshot as RouterStateSnapshot).root.queryParams;
    // const queryParamsKey = Object.keys(queryParams);
    // if (queryParamsKey && queryParamsKey.includes('id')) {
    //   this.dashboardService.selectedData = queryParams['id'];
    // }

    this.route.queryParams.subscribe((params) => {
      this.dashboardService.selectedData = params['id'];
      // console.log('Query parameter ID changed:', this.selectedId);
      // Refresh or load data based on the new ID
      // this.ngOnInit();
      if(params['id'] === '1'){
        this.dashBoardInitalDataFn();

      }else if(params['id'] === '2'){
        this.prepareSearchData('reset');
      }
    });

    this.chartOptionsRegionwise = {
      chart: {
        type: 'bar',
        height: 350,
        toolbar: {
          show: false // Optional: Hide toolbar if not needed
        }
      },
      title: {
        text: 'Region Wise Monthly Distibution.', // Chart title
        align: 'center',
        style: {
          fontSize: '16px',
          fontWeight: 'bold',
          color: '#333'
        }
      },
      series: [
        {
          name: 'Number of Retailers',
          data: [120, 150, 130] // Example values for Number of Retailers
        },
        {
          name: 'Quantity (k)',
          data: [30, -25, 35] // Example values for Quantity
        },
        {
          name: 'Value',
          data: [100, -70, 80] // Example values for Value
        }
      ],
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '50%'
          // endingShape: "rounded"
        }
      },
      dataLabels: {
        enabled: true
      },
      stroke: {
        show: true,
        width: 2,
        colors: ['transparent']
      },
      xaxis: {
        categories: [
          'Feb',
          'Mar',
          'Apr'
          // "May",
          // "Jun",
          // "Jul",
          // "Aug",
          // "Sep",
          // "Oct"
        ]
      },
      yaxis: {
        title: {
          text: ' (Growth percentage)'
        },
        min: -100 // Set a minimum value for y-axis to accommodate negative values
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

    this.chartOptionsRegionwiseGrowth = {
      chart: {
        type: 'bar',
        height: 350,
        toolbar: {
          show: false // Optional: Hide toolbar if not needed
        }
      },
      title: {
        text: 'Region Wise Growth Over Previous Month.', // Chart title
        align: 'center',
        style: {
          fontSize: '16px',
          fontWeight: 'bold',
          color: '#333'
        }
      },
      series: [
        {
          name: 'Number of Retailers',
          data: [120, 150, 130] // Example values for Number of Retailers
        },
        {
          name: 'Quantity (k)',
          data: [30, -25, 35] // Example values for Quantity
        },
        {
          name: 'Value',
          data: [100, -70, 80] // Example values for Value
        }
      ],
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '50%'
          // endingShape: "rounded"
        }
      },
      dataLabels: {
        enabled: true
      },
      stroke: {
        show: true,
        width: 2,
        colors: ['transparent']
      },
      xaxis: {
        categories: [
          'Feb',
          'Mar',
          'Apr'
          // "May",
          // "Jun",
          // "Jul",
          // "Aug",
          // "Sep",
          // "Oct"
        ]
      },
      yaxis: {
        title: {
          text: ' (Growth percentage)'
        },
        min: -100 // Set a minimum value for y-axis to accommodate negative values
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

  // life cycle event
  async ngOnInit() {

    // this.budgetChartOptions = {
    //   series: [
    //     {
    //       name: 'East',
    //       group: 'retailers',
    //       data: [44000, 55000, 41000, 67000, 22000, 43000]
    //     },
    //     {
    //       name: 'Q1 Actual',
    //       group: 'actual',
    //       data: [48000, 50000, 40000, 65000, 25000, 40000]
    //     },
    //     {
    //       name: 'North',
    //       group: 'retailers',
    //       data: [13000, 36000, 20000, 8000, 13000, 27000]
    //     },
    //     {
    //       name: 'Q2 Actual',
    //       group: 'actual',
    //       data: [20000, 40000, 25000, 10000, 12000, 28000]
    //     }
    //   ],
    //   chart: {
    //     type: 'bar',
    //     height: 350,
    //     stacked: true
    //   },
    //   stroke: {
    //     width: 1,
    //     colors: ['#fff']
    //   },
    //   // dataLabels: {
    //   //   formatter: (value) => {
    //   //     return value / 1000 + 'K';
    //   //   }
    //   // },
    //   plotOptions: {
    //     bar: {
    //       horizontal: false
    //     }
    //   },
    //   xaxis: {
    //     categories: [
    //       'April',
    //       'May',
    //     ]
    //   },
    //   fill: {
    //     opacity: 1
    //   },
    //   colors: ['#80c7fd', '#008FFB', '#80f1cb', '#00E396'],
    //   yaxis: {
    //     labels: {
    //       formatter: (value) => {
    //         return value / 1000 + 'K';
    //       }
    //     }
    //   },
    //   legend: {
    //     position: 'top',
    //     horizontalAlign: 'left'
    //   }
    // };

    // this.budgetChartOptions = {
    //   series: [
    //     {
    //       name: 'Retailers - East',
    //       group: 'retailers',
    //       data: [44000, 55000, 41000]
    //     },
    //     {
    //       name: 'Qty - East',
    //       group: 'qty',
    //       data: [12000, 15000, 13000]
    //     },
    //     {
    //       name: 'Value - East',
    //       group: 'value',
    //       data: [32000, 45000, 39000]
    //     },
    //     {
    //       name: 'Retailers - South2',
    //       group: 'retailers',
    //       data: [38000, 50000, 40000]
    //     },
    //     {
    //       name: 'Qty - South2',
    //       group: 'qty',
    //       data: [11000, 14000, 12500]
    //     },
    //     {
    //       name: 'Value - South2',
    //       group: 'value',
    //       data: [31000, 42000, 37000]
    //     },
    //     {
    //       name: 'Retailers - North',
    //       group: 'retailers',
    //       data: [46000, 52000, 44000]
    //     },
    //     {
    //       name: 'Qty - North',
    //       group: 'qty',
    //       data: [15000, 18000, 16000]
    //     },
    //     {
    //       name: 'Value - North',
    //       group: 'value',
    //       data: [35000, 49000, 42000]
    //     },
    //     {
    //       name: 'Retailers - West',
    //       group: 'retailers',
    //       data: [42000, 48000, 43000]
    //     },
    //     {
    //       name: 'Qty - West',
    //       group: 'qty',
    //       data: [13000, 16000, 15000]
    //     },
    //     {
    //       name: 'Value - West',
    //       group: 'value',
    //       data: [33000, 46000, 40000]
    //     },
    //     {
    //       name: 'Retailers - 2',
    //       group: 'retailers',
    //       data: [39000, 53000, 47000]
    //     },
    //     {
    //       name: 'Qty - 2',
    //       group: 'qty',
    //       data: [14000, 17000, 14500]
    //     },
    //     {
    //       name: 'Value - 2',
    //       group: 'value',
    //       data: [34000, 48000, 43000]
    //     }
    //   ],
    //   chart: {
    //     type: 'bar',
    //     height: 350,
    //     stacked: true
    //   },
    //   stroke: {
    //     width: 1,
    //     colors: ['#fff']
    //   },
    //   plotOptions: {
    //     bar: {
    //       horizontal: false
    //     }
    //   },
    //   xaxis: {
    //     categories: ['April', 'May', 'June']
    //   },
    //   fill: {
    //     opacity: 1
    //   },
    //   colors: ['#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#FF4560', '#775DD0', '#FEB019', '#FF9800', '#33B2DF', '#546E7A', '#D4526E', '#13D8AA', '#A5978B', '#4CAF50', '#D50000'],
    //   yaxis: {
    //     labels: {
    //       formatter: (value:any) => {
    //         return value / 1000 + 'K';
    //       }
    //     }
    //   },
    //   legend: {
    //     position: 'top',
    //     horizontalAlign: 'left'
    //   }
    // };
    
   
    
    console.log('calling count')
    this.spinner.show();
    const GrowthOverPreviousMonthPayload :any = {
      "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
        "startDate":20240401,       /// default case start date of financial year in integer format
        "endDate": 202401030,       ////  default case end date of financial year in integer format
        "brandList": "",       //// default casen ""
        "rsNameList": "",//// default casen ""
        "retailerType":"",
      };

    // if (this.dashboardService.selectedData === '2') {
    //   this.GetMasterData();
    //   this.MonthlyToalOrdaring(GrowthOverPreviousMonthPayload);
    //   this.GrowthOverPreviousMonth(GrowthOverPreviousMonthPayload);
    // } else if (this.dashboardService.selectedData === '1') {
    //   await this.dashBoardInitalDataFn();
    // }
    // this.isLoading = false;
    // setTimeout(() => {
    //   // Hide the spinner after the delay
    //   this.spinner.hide();
    // }, 3000);
  }

  // this is to get the searched data;
  public prepareSearchData(data: any) {
    let MonthlyToalOrdaringPayload: any = {};
    let abmNameList: any = '';
    let brandList: any = '';
    let rsNameList: any = '';
    let regionList: any = '';
    let retailerTypeList: any = '';

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

        this.selectedAbmNames = [];
        this.selectedBrands = [];
        this.selectedRSNames = [];
        this.selectedRegions = [];
        this.selectedRetailerTypes = [];
    }
    this.GetMasterData();
    this.MonthlyToalOrdaring(MonthlyToalOrdaringPayload);
    this.GrowthOverPreviousMonth(MonthlyToalOrdaringPayload);
    this.RegionWiseMonthlyDistribution(MonthlyToalOrdaringPayload);
    this.RegionWiseGrowthOverPreviousMonth(MonthlyToalOrdaringPayload);
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
        height: 350,
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
      markers: {
        size: 5, // Size of markers on the line
        hover: {
          sizeOffset: 6
        }
      },
      xaxis: {
        labels: {
          trim: false
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
            formatter: function (val) {
              // return '' + val; // Format for value
              return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
            }
          },
          tickAmount: 4 // Adjust the number of ticks as necessary
        },
        {
          opposite: true, // Make this y-axis on the opposite side
          title: {
            text: 'Value (Cr)', // Right y-axis title
            style: {
              color: '#000000' // Change color as needed
            }
          },
           labels: {
            formatter: function (val) {
              // return '' + val; // Format for value
              return val.toFixed(0);
            }
          },
          min: 0, // Minimum value for right y-axis
          tickAmount: 4 // Adjust the number of ticks as necessary
        }
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

          response.body.forEach((item: any) => {
            // Push values to each series array
            retailersData.push(item.totalRetailerCode);
            quantityData.push(item.totalQTY /1000);
            valueData.push(item.totalRevenue / 10000000);

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });
          this.chartOptionsline = {
            series: [
              // {
              //   name: 'Retailers',
              //   data: [120, 150, 130] // Example data for Retailers over 3 months
              // },
              // {
              //   name: 'Quantity (k)',
              //   data: [30, 25, 35] // Example data for Quantity over 3 months
              // },
              // {
              //   name: 'Value',
              //   data: [100, 70, 80] // Example data for Value over 3 months (in thousands)
              // }
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
              height: 350,
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
              text: 'Monthly Trend',
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
                trim: false
              },
              categories: categories
            },
            yaxis: [
              {
                title: {
                  text: 'Quantity (K)', // Left y-axis title
                  style: {
                    color: '#000000' // Change color as needed
                  }
                },
                min: 0, // Minimum value for left y-axis
                labels: {
                  formatter: function (val) {
                    // return '' + val; // Format for value
                    return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
                  }
                },
                tickAmount: 4 // Adjust the number of ticks as necessary
              },
              {
                opposite: true, // Make this y-axis on the opposite side
                title: {
                  text: 'Value (Cr)', // Right y-axis title
                  style: {
                    color: '#000000' // Change color as needed
                  }
                },
                 labels: {
                  formatter: function (val) {
                    // return '' + val; // Format for value
                    return val.toFixed(0);
                  }
                },
                min: 0, // Minimum value for right y-axis
                tickAmount: 4 // Adjust the number of ticks as necessary
              }
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
              height: 350,
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
                fontWeight: 'bold',
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
                columnWidth: '50%'
                // endingShape: "rounded"
              }
            },
            dataLabels: {
              enabled: false
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
                  formatter: function (val:any) {
                    return '' + val; // Format for value
                  }
                },
                tickAmount: 4 // Adjust the number of ticks as necessary
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

          response.body.forEach((item: any) => {
            // Push values to each series array
            // retailersData.push(item.retailerGrowth);
            // quantityData.push(item.orderGrowth);
            // valueData.push(item.priceGrowth);
            retailersData.push(item.retailerGrowth);
            quantityData.push(item.orderGrowth / 1000); // Converts orderGrowth to thousands
            valueData.push(item.priceGrowth / 10000000); // Converts priceGrowth to crores

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });

          this.chartOptions = {
            chart: {
              type: 'bar',
              height: 350,
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
              text: 'Growth Over Previous Month.', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                fontWeight: 'bold',
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
                columnWidth: '50%'
                // endingShape: "rounded"
              }
            },
            dataLabels: {
              enabled: false
            },
            stroke: {
              show: true,
              width: 2,
              colors: ['transparent']
            },
            xaxis: {
              categories: categories
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
                  text: ' (Growth Value)', // Left y-axis title
                  style: {
                    color: '#000000' // Change color as needed
                  }
                },
                // min: 0, // Minimum value for left y-axis
                labels: {
                  formatter: function (val:any) {
                    return '' + val; // Format for value
                  }
                },
                tickAmount: 4 // Adjust the number of ticks as necessary
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
  }

  // Remove a selected region
  removeSelectedRegion(region: { id: number; name: string }, event: MouseEvent) {
    event.stopPropagation(); // Prevent dropdown from closing
    this.toggleRegionSelection(region); // Toggle selection
  }

  // Select or deselect all regions
  toggleAllRegionsSelection(event: any) {
    this.selectedRegions = event.target.checked ? [...this.availableRegions] : []; // Select/Deselect all
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
  }

  // Remove a selected ABM name
  removeSelectedAbm(abm: { id: number; name: string }, event: MouseEvent) {
    event.stopPropagation(); // Prevent dropdown from closing
    this.toggleAbmSelection(abm); // Toggle selection
  }

  // Select or deselect all ABM names
  toggleAllAbmSelection(event: any) {
    this.selectedAbmNames = event.target.checked ? [...this.availableAbmNames] : []; // Select/Deselect all
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

  GetMasterData(): void {
    // this.spinner.show();
    this.dashboardService.GetMasterData().subscribe(
      (response) => {
        // this.spinner.hide();

        if (response && response.region && response.region.length > 0) {
          const availableRegions = response.region.map((region: any, index: any) => {
            return {
              id: index + 1, // Assign unique IDs starting from 1
              name: region.replace(' ', '') // Replace space in 'SOUTH 1' and 'SOUTH 2' with empty string
            };
          });
          this.availableRegions = availableRegions;
        }
        if (response && response.brand && response.brand.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableBrands: Brand[] = response.brand.map(([id, name]: [string, string]) => {
            return {
              id: parseInt(id), // Convert ID to a number
              name: name // Keep the name as is
            };
          });
          this.availableBrands = availableBrands;
        }

        if (response && response.rsName && response.rsName.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableRSNames: Brand[] = response.rsName.map(([id, name]: [string, string]) => {
            return {
              id: parseInt(id), // Convert ID to a number
              name: name // Keep the name as is
            };
          });
          this.availableRSNames = availableRSNames;
        }
        if (response && response.abmName && response.abmName.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableAbmNames: Brand[] = response.abmName.map(([id, name]: [string, string]) => {
            return {
              id: id, // Convert ID to a number
              name: name // Keep the name as is
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
              height: 350,
              toolbar: {
                show: false // Optional: Hide toolbar if not needed
              }
            },
            title: {
              text: 'Orders Monthly Details.', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                fontWeight: 'bold',
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
              enabled: true
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

  public RegionWiseMonthlyDistribution = (MonthlyToalOrdaringPayload?: any) => {
    this.spinner.show();
          // Define month names for easy mapping
          const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
          // Prepare data for each series
          const retailersData :any= [];
          const quantityData :any= [];
          const valueData:any = [];
          const categories: string[] = []; // This will hold the month names

          this.RegionWiseMonthlyDistibutionOptions = {
            series: [
              // Retailers group
              { name: 'Retailers - East', group: 'retailers', data: [44, 55, 41] },
              { name: 'Retailers - South1', group: 'retailers', data: [38, 50, 40] },
              { name: 'Retailers - South2', group: 'retailers', data: [38, 50, 40] },
              { name: 'Retailers - North', group: 'retailers', data: [46, 52, 44] },
              { name: 'Retailers - West', group: 'retailers', data: [42, 48, 43] },
          
              // Qty group
              { name: 'Qty - East', group: 'qty', data: [12, 15, 13] },
              { name: 'Qty - South1', group: 'qty', data: [11, 14, 12.5] },
              { name: 'Qty - South2', group: 'qty', data: [11, 14, 12.5] },
              { name: 'Qty - North', group: 'qty', data: [15, 18, 16] },
              { name: 'Qty - West', group: 'qty', data: [13, 16, 15] },
          
              // Value group
              { name: 'Value - East', group: 'value', data: [32, 45, 39] },
              { name: 'Value - South1', group: 'value', data: [31, 42, 37] },
              { name: 'Value - South2', group: 'value', data: [31, 42, 37] },
              { name: 'Value - North', group: 'value', data: [35, 49, 42] },
              { name: 'Value - West', group: 'value', data: [33, 46, 40] }
            ],
            title: {
              text: 'Region Wise Distibution Loading....', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                fontWeight: 'bold',
                color: '#333'
              }
            },
            dataLabels: {
              enabled: false
            },
            chart: {
              type: 'bar',
              height: 350,
              stacked: true
            },
            xaxis: {
              categories: ['April', 'May', 'June']
            },
            plotOptions: {
              bar: {
                horizontal: false
              }
            },
            fill: {
              opacity: 1
            },
            colors: [
              '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019',  // Retailers colors
              '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845',  // Qty colors
              '#2ECC71', '#28B463', '#239B56', '#1D8348', '#186A3B'   // Value colors
            ],
            legend: {
              position: 'top',
              horizontalAlign: 'left'
            }
          };
    this.dashboardService.RegionWiseMonthlyDistribution(MonthlyToalOrdaringPayload).subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();

          response.body.forEach((item: any) => {
            // Push values to each series array
            retailersData.push(item.totalRetailerCode);
            quantityData.push(item.totalQTY /1000);
            valueData.push(item.totalRevenue / 10000000);

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });
          this.RegionWiseMonthlyDistibutionOptions = {
            series: [
              // Retailers group
              { name: 'Retailers - East', group: 'retailers', data: [44, 55, 41] },
              { name: 'Retailers - South1', group: 'retailers', data: [38, 50, 40] },
              { name: 'Retailers - South2', group: 'retailers', data: [38, 50, 40] },
              { name: 'Retailers - North', group: 'retailers', data: [46, 52, 44] },
              { name: 'Retailers - West', group: 'retailers', data: [42, 48, 43] },
          
              // Qty group
              { name: 'Qty - East', group: 'qty', data: [12, 15, 13] },
              { name: 'Qty - South1', group: 'qty', data: [11, 14, 12.5] },
              { name: 'Qty - South2', group: 'qty', data: [11, 14, 12.5] },
              { name: 'Qty - North', group: 'qty', data: [15, 18, 16] },
              { name: 'Qty - West', group: 'qty', data: [13, 16, 15] },
          
              // Value group
              { name: 'Value - East', group: 'value', data: [32, 45, 39] },
              { name: 'Value - South1', group: 'value', data: [31, 42, 37] },
              { name: 'Value - South2', group: 'value', data: [31, 42, 37] },
              { name: 'Value - North', group: 'value', data: [35, 49, 42] },
              { name: 'Value - West', group: 'value', data: [33, 46, 40] }
            ],
            title: {
              text: 'Region Wise Distibution', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                fontWeight: 'bold',
                color: '#333'
              }
            },
            dataLabels: {
              enabled: false
            },
            chart: {
              type: 'bar',
              height: 350,
              stacked: true
            },
            xaxis: {
              categories: ['April', 'May', 'June']
            },
            plotOptions: {
              bar: {
                horizontal: false
              }
            },
            fill: {
              opacity: 1
            },
            colors: [
              '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019',  // Retailers colors
              '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845',  // Qty colors
              '#2ECC71', '#28B463', '#239B56', '#1D8348', '#186A3B'   // Value colors
            ],
            legend: {
              position: 'top',
              horizontalAlign: 'left'
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

  public RegionWiseGrowthOverPreviousMonth = (MonthlyToalOrdaringPayload?: any) => {
    this.spinner.show();
          // Define month names for easy mapping
          const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
          // Prepare data for each series
          const retailersData :any= [];
          const quantityData :any= [];
          const valueData:any = [];
          const categories: string[] = []; // This will hold the month names

          this.RegionWiseGrowthOverPreviousMonthOptions = {
            series: [
              // Retailers group
              { name: 'Retailers - East', group: 'retailers', data: [44, 55, 41] },
              { name: 'Retailers - South1', group: 'retailers', data: [38, 50, 40] },
              { name: 'Retailers - South2', group: 'retailers', data: [38, 50, 40] },
              { name: 'Retailers - North', group: 'retailers', data: [46, 52, 44] },
              { name: 'Retailers - West', group: 'retailers', data: [42, 48, 43] },
          
              // Qty group
              { name: 'Qty - East', group: 'qty', data: [12, 15, 13] },
              { name: 'Qty - South1', group: 'qty', data: [11, 14, 12.5] },
              { name: 'Qty - South2', group: 'qty', data: [11, 14, 12.5] },
              { name: 'Qty - North', group: 'qty', data: [15, 18, 16] },
              { name: 'Qty - West', group: 'qty', data: [13, 16, 15] },
          
              // Value group
              { name: 'Value - East', group: 'value', data: [32, 45, 39] },
              { name: 'Value - South1', group: 'value', data: [31, 42, 37] },
              { name: 'Value - South2', group: 'value', data: [31, 42, 37] },
              { name: 'Value - North', group: 'value', data: [35, 49, 42] },
              { name: 'Value - West', group: 'value', data: [33, 46, 40] }
            ],
            title: {
              text: 'Region Wise Distibution Growth Loading....', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                fontWeight: 'bold',
                color: '#333'
              }
            },
            dataLabels: {
              enabled: false
            },
            chart: {
              type: 'bar',
              height: 350,
              stacked: true
            },
            xaxis: {
              categories: ['April', 'May', 'June']
            },
            plotOptions: {
              bar: {
                horizontal: false
              }
            },
            fill: {
              opacity: 1
            },
            colors: [
              '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019',  // Retailers colors
              '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845',  // Qty colors
              '#2ECC71', '#28B463', '#239B56', '#1D8348', '#186A3B'   // Value colors
            ],
            legend: {
              position: 'top',
              horizontalAlign: 'left'
            }
          };
    this.dashboardService.RegionWiseGrowthOverPreviousMonth(MonthlyToalOrdaringPayload).subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();

          response.body.forEach((item: any) => {
            // Push values to each series array
            retailersData.push(item.totalRetailerCode);
            quantityData.push(item.totalQTY /1000);
            valueData.push(item.totalRevenue / 10000000);

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });
          this.RegionWiseGrowthOverPreviousMonthOptions = {
            series: [
              // Retailers group
              { name: 'Retailers - East', group: 'retailers', data: [44, 55, 41] },
              { name: 'Retailers - South1', group: 'retailers', data: [38, 50, 40] },
              { name: 'Retailers - South2', group: 'retailers', data: [38, 50, 40] },
              { name: 'Retailers - North', group: 'retailers', data: [46, 52, 44] },
              { name: 'Retailers - West', group: 'retailers', data: [42, 48, 43] },
          
              // Qty group
              { name: 'Qty - East', group: 'qty', data: [12, 15, 13] },
              { name: 'Qty - South1', group: 'qty', data: [11, 14, 12.5] },
              { name: 'Qty - South2', group: 'qty', data: [11, 14, 12.5] },
              { name: 'Qty - North', group: 'qty', data: [15, 18, 16] },
              { name: 'Qty - West', group: 'qty', data: [13, 16, 15] },
          
              // Value group
              { name: 'Value - East', group: 'value', data: [32, 45, 39] },
              { name: 'Value - South1', group: 'value', data: [31, 42, 37] },
              { name: 'Value - South2', group: 'value', data: [31, 42, 37] },
              { name: 'Value - North', group: 'value', data: [35, 49, 42] },
              { name: 'Value - West', group: 'value', data: [33, 46, 40] }
            ],
            title: {
              text: 'Region Wise Growth Distibution', // Chart title
              align: 'center',
              style: {
                fontSize: '16px',
                fontWeight: 'bold',
                color: '#333'
              }
            },
            dataLabels: {
              enabled: false
            },
            chart: {
              type: 'bar',
              height: 350,
              stacked: true
            },
            xaxis: {
              categories: ['April', 'May', 'June']
            },
            plotOptions: {
              bar: {
                horizontal: false
              }
            },
            fill: {
              opacity: 1
            },
            colors: [
              '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019',  // Retailers colors
              '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845',  // Qty colors
              '#2ECC71', '#28B463', '#239B56', '#1D8348', '#186A3B'   // Value colors
            ],
            legend: {
              position: 'top',
              horizontalAlign: 'left'
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
}
