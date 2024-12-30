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
type DropdownFlag =
  | 'isDropdownOpenForRetailer'
  | 'isDropdownOpenForRS'
  | 'isDropdownOpenForBrand'
  | 'isDropdownOpen'
  | 'isDropdownOpenForAbm'
  | 'isDropdownOpenForMonth'
  | 'isDropdownOpenForYear'
  | 'isDropdownOpenForState'
  | 'isDropdownOpenForCity'|
  'isDropdownOpenForSelectionType';

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
import { DatePipe } from '@angular/common';
import { left } from '@popperjs/core';

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
  styleUrls: ['./dash-analytics.component.scss'],
  providers: [DatePipe] // Provide DatePipe here
})
export default class DashAnalyticsComponent {
  chartOptionsline!: ApexOptions; // Change to ApexOptions
  chartOptionsRegionwise!: ApexOptions;
  chartOptionsRegionwiseGrowth!: ApexOptions;
  regionWiseGrowthOptions!: ApexOptions;
  budgetChartOptions!: ApexOptions;
  RegionWiseMonthlyDistibutionOptions!: ApexOptions;
  RegionWiseGrowthOverPreviousMonthOptions!: ApexOptions;
  RegionWiseGrowthOverPreviousMonthOptionsForPrevYear!: ApexOptions;
  RegionWiseGrowthOverPreviousMonthOptionsPrevYear!: ApexOptions;

  // This is for menu id 3
  chartOptionslineForOrdBh!: ApexOptions; // Change to ApexOptions
  RegionWiseMonthlyDistibutionOptionsFOrdBh!: ApexOptions;
  RegionWiseMonthlyAvgPerOrder!: ApexOptions;

  public appendClass: any;

  // percenate of orders cnharts.

  percentageOfOrdersOfDayInMonthOptions!: ApexOptions;
  percentageofOrdersbyWeekdayorWeekendOptionsPiechart!: ApexOptions;
  percentageofOrdersbyWeekdayorWeekendOptionsBarchart!: ApexOptions;
  percentageofOrdersByWeekdayorWeekendRegionWiseOptions!: ApexOptions;
  percentageofOrdersByHourOfTheDayOptionsBarchart!: ApexOptions;

  @HostListener('window:click', ['$event'])
  onWindowClick(event: MouseEvent) {
    // Ignore clicks on the specific button with id 'allowClickButton'
    const targetElement = event.target as HTMLElement;

    if (
      targetElement.closest('#allowClickButton') ||
      targetElement.closest('#allowClickButtonabm') ||
      targetElement.closest('#restrictclicksabm') ||
      targetElement.closest('#restrictclicks') ||
      targetElement.closest('#retailerTypeallow') ||
      targetElement.closest('#retailerTypeallow') ||
      targetElement.closest('#retailerTypeallowDropdown') ||
      targetElement.closest('#rsnamemenu2') ||
      targetElement.closest('#rsnamemenu') ||
      targetElement.closest('#brandDropdown') ||
      targetElement.closest('#brandDropdownmenu') ||
      targetElement.closest('#monthDropdownmenu') ||
      targetElement.closest('#monthDropdown') ||
      targetElement.closest('#yearDropdown') ||
      targetElement.closest('#yearDropdownmenu')||
      targetElement.closest('#stateDropdown')||
      targetElement.closest('#stateDropdownmenu')||
      targetElement.closest('#cityDropdown') ||
      targetElement.closest('#cityDropdownmenu') ||
      targetElement.closest('#selectionTypeDropdown') ||
      targetElement.closest('#selectionTypeDropdownMenu')
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
    if(this.isDropdownOpenForSelectionType){
      this.isDropdownOpenForSelectionType = !this.isDropdownOpenForSelectionType;
    }
    if (this.isDropdownOpenForRS) {
      this.isDropdownOpenForRS = !this.isDropdownOpenForRS;
    }
    if (this.isDropdownOpenForBrand) {
      this.isDropdownOpenForBrand = !this.isDropdownOpenForBrand;
    }
    if (this.isDropdownOpenForMonth) {
      this.isDropdownOpenForMonth = !this.isDropdownOpenForMonth;
    }

    if (this.isDropdownOpenForYear) {
      this.isDropdownOpenForYear = !this.isDropdownOpenForYear;
    }
    if(this.isDropdownOpenForState){
      this.isDropdownOpenForState = !this.isDropdownOpenForState;
    }
    if(this.isDropdownOpenForCity){
      this.isDropdownOpenForCity = !this.isDropdownOpenForCity;   
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
    { id: 3, name: 'South 1' },
    { id: 4, name: 'South 2' },
    { id: 5, name: 'West' }
  ];


  
  availableRegions: any = [
    { id: 1, name: 'East' },
    { id: 2, name: 'North' },
    { id: 3, name: 'South 1' },
    { id: 4, name: 'South 2' },
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
  growthOverPreviousMonthYear!: ApexOptions;
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

  orders = [
    { id: 'hn3434ff00', orderQty: 1200, region: 'North', rs: 'RS1' },
    { id: 'hn3434ff01', orderQty: 1500, region: 'South', rs: 'RS2' },
    { id: 'hn3434ff02', orderQty: 1300, region: 'East', rs: 'RS3' },
    { id: 'hn3434ff03', orderQty: 1100, region: 'West', rs: 'RS1' },
    { id: 'hn3434ff04', orderQty: 1400, region: 'North', rs: 'RS2' }
  ];
  overallGrandTotal: number = 0;
  overallRegionGrandTotal: number = 0;
  overallRsGrandTotal: number = 0;

  overallOrders: any;
  overAllResponseOrderLevel:any;
  overAllResponseRegionLevel:any;
  overAllResponseRsOrderLevel:any;

  regionTotals: any;
  rsTotals: any;

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
  currentMonthToDateText: string = 'Loading...';
  getLastUpdatedRecordDate: string = '';
selectedOrder:any = '';
selectedOrderForRegion:any='';
selectedOrderForRs:any='';
currentOverallLevel:any = 0;
selectedImageSource:any = '';
selectedImageSourceForRegion:any = '';
selectedImageSourceForRs:any = '';
LoadingDataForSku:any={};








// this is for state filter 

isDropdownOpenForState = false;
  selectedStates: string[] = [];
  availableStates: string[] = [];
  filteredStatesList: string[] = [...this.availableStates];
  areAllStatesSelected = false;
  searchInputValueForState = '';


  isDropdownOpenForCity = false;
  selectedCities: string[] = [];
  availableCities: string[] = [
    // "Agarmalwa",
    // "AGARTALA",
    // "AJEETGARH",
    // "AKHNOOR",
    // "AKOLA",
    // "ALAMBAZAR",
    // "ALUVA",
  ];
  filteredCitiesList: string[] = [...this.availableCities];
  areAllCitiesSelected = false;
  searchInputValueForCity = '';

  displayShowMore:any = true;
  displayShowMoreForRegionOrderLevel:any = true;
  displayShowMoreForRsOrderLevel:any = true;

  displayShowMoreForRegion:any = true;
  displayShowMoreForRSWise:any = true;



// to get the selection type for skus.

selectedSelectionType: string | null = 'Order Wise';
selectedSelectionTypeForUI: string | null = 'Order Wise';

isDropdownOpenForSelectionType = false;
searchInputValueForSelectionType: string = '';
filteredSelectionTypesList: string[] = ['Order Wise', 'Value wise'];
allSelectionTypesList: string[] = ['Order Wise', 'Value wise'];




  // constructor
  constructor(
    public dashboardService: DashboardService,
    private spinner: NgxSpinnerService,
    private router: Router,
    private route: ActivatedRoute,
    private datePipe: DatePipe
  ) {
    // this.selectedAbmNames = [];
    // this.selectedBrands = [];
    // this.selectedRSNames = [];
    // this.selectedRegions = [];
    // this.selectedRetailerTypes = [];
    // this.selectedMonths = [];

    this.getCurrentMonthToDate();
    this.route.queryParams.subscribe((params) => {
      this.dashboardService.selectedData = params['id'];
      this.initializeYears();
    
      if (params['id'] === '1') {
        // Initialize years
       
        // Get the current date
        const today = new Date();
    
        // Calculate start and end dates for the current month
        const startDate = new Date(today.getFullYear(), today.getMonth(), 1);
        const endDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);
    
        // Helper function to format dates as YYYYMMDD
        const formatDate = (date: Date) => 
          date.getFullYear() * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
    
       
    
        // Set selected month
        const currentMonthId = today.getMonth() + 1;
        const currentMonth = this.availableMonths.find((month) => month.id === currentMonthId);
        this.selectedMonths = currentMonth ? [currentMonth] : [];
    
        // Set selected year
        const currentYear = today.getFullYear();
        const currentYearInList = this.yearsList.find((year) => year === currentYear);
        this.selectedYears = currentYearInList ? [currentYearInList] : [];

        const selectedMonths = this.selectedMonths.map((item) => item.id).join(', ');
        const selectedYears = this.selectedYears.map((item) => item).join(', ');

     // Prepare payload
     const GrowthOverPreviousMonthPayload: any = {
      startDate: formatDate(startDate),
      endDate: formatDate(endDate),
      selectedMonths:selectedMonths,
      selectedYears:selectedYears
      // Add other fields if required
    };
        // Fetch initial dashboard data
        this.dashBoardInitalDataFn(GrowthOverPreviousMonthPayload);
      } else if (params['id']) {
        this.prepareSearchData('reset');
      }
    });
    
  }

  // life cycle event
  async ngOnInit() {
    console.log('calling count');
    this.spinner.show();
  }

  // generateFinancialYearPayload(months: any) {
  //   const year = new Date().getFullYear(); // Gets the current year

  //   if (!months || months.length === 0) {
  //     // Default dates if months array is empty
  //     return { startDate: 20240401, endDate: 202401030 };
  //   }

  //   // Find the minimum and maximum month IDs in the array
  //   let startMonth = months[0];
  //   let endMonth = months[0];

  //   months.forEach((month: any) => {
  //     if (month.id < startMonth.id) {
  //       startMonth = month;
  //     }
  //     if (month.id > endMonth.id) {
  //       endMonth = month;
  //     }
  //   });
  //   const startDate = parseInt(`${year}${this.formatMonthId(startMonth.id)}01`); // 1st day of the start month
  //   const endDate = parseInt(`${year}${this.formatMonthId(endMonth.id)}30`); // 30th day o
  //   return {
  //     startDate,
  //     endDate
  //   };
  // }
  generateFinancialYearPayload(months: any) {
    const currentYear = new Date().getFullYear(); // Get the current year
  
    // Check if any year is selected, otherwise default to current year
    const selectedStartYear = this.selectedYears.length > 0 ? Math.min(...this.selectedYears) : currentYear;
    const selectedEndYear = this.selectedYears.length > 0 ? Math.max(...this.selectedYears) : currentYear;
  
    // if (!months || months.length === 0) {
    //   // Default dates if months array is empty (use the selectedStartYear and selectedEndYear)
    //   return { startDate: parseInt(`${selectedStartYear}0401`), endDate: parseInt(`${selectedEndYear}1231`) };
    // }

    if (!months || months.length === 0) {
      // Set start date to April 1st of the selectedStartYear
      const startDate = parseInt(`${selectedStartYear}0401`);
    
      // Get current date and determine the last day of the current month
      const currentDate = new Date();
      const currentMonth = currentDate.getMonth(); // 0-based index (0 = January)
      const currentYear = currentDate.getFullYear();
    
      // Get the last day of the current month
      const endDate = new Date(currentYear, currentMonth + 1, 0); // This gives the last day of the current month
      const endDateFormatted = parseInt(`${endDate.getFullYear()}${(endDate.getMonth() + 1).toString().padStart(2, '0')}${endDate.getDate().toString().padStart(2, '0')}`);
    
      return { startDate, endDate: endDateFormatted };
    }
    
  
    // Find the minimum and maximum month IDs in the array
    let startMonth = months[0];
    let endMonth = months[0];
  
    months.forEach((month: any) => {
      if (month.id < startMonth.id) {
        startMonth = month;
      }
      if (month.id > endMonth.id) {
        endMonth = month;
      }
    });
  
    // Helper function to format month ID (e.g., '01' for January)
    const formatMonthId = (monthId: number) => (monthId < 10 ? `0${monthId}` : `${monthId}`);
  
    // Get the start date for the selected start month
    const startDate = parseInt(`${selectedStartYear}${formatMonthId(startMonth.id)}01`); // 1st day of the start month
  
    // Helper function to get the last day of a month (handles different month lengths)
    const getLastDayOfMonth = (year: number, month: number) => {
      // Set the date to the 1st of the next month, then subtract one day
      const date = new Date(year, month, 0);
      return date.getDate(); // This will give us the last day of the month
    };
  
    // Get the last day of the end month (could be 28, 29, 30, or 31)
    const lastDayOfEndMonth = getLastDayOfMonth(selectedEndYear, endMonth.id);
  
    // Get the end date for the selected end month
    const endDate = parseInt(`${selectedEndYear}${formatMonthId(endMonth.id)}${lastDayOfEndMonth}`); // Last day of the end month
  
    return {
      startDate,
      endDate
    };
  }
  
  formatMonthId(monthId: number): string {
    return monthId < 10 ? `0${monthId}` : `${monthId}`;
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
    let payloadForMaster: any;
    const selectedCities = this.selectedCities.map((item) => item).join(', ');
    const selectedStates = this.selectedStates.map((item) => item).join(', ');

    if (data === 'search') {

      const selectedMonths = this.selectedMonths.map((item) => item.id).join(', ');
      const selectedYears = this.selectedYears.map((item) => item).join(', ');

      // Prepare comma-separated strings for each array
      abmNameList = this.selectedAbmNames.map((item) => item.id).join(', ');
      brandList = this.selectedBrands.map((item) => item.name).join(', ');
      rsNameList = this.selectedRSNames.map((item) => item.name).join(', ');
      regionList = this.selectedRegions.map((item) => item.name).join(', ');
      retailerTypeList = this.selectedRetailerTypes.map((item) => item.name).join(', ');
      const payload = this.generateFinancialYearPayload(this.selectedMonths);

      MonthlyToalOrdaringPayload = {
        regionList: regionList && regionList.length > 0 ? regionList : 'EAST, WEST,NORTH,SOUTH 1,SOUTH 2', /// default all regions
        startDate: payload.startDate, /// default case start date of financial year in integer format
        endDate: payload.endDate, ////  default case end date of financial year in integer format
        brandList: brandList, //// default casen ""
        rsNameList: rsNameList, //// default casen ""
        abmName: abmNameList,
        retailerType: retailerTypeList,
        selectedMonths:selectedMonths,
        selectedYears:selectedYears,
        selectedCity:selectedCities,
        selectedState:selectedStates
      };
      payloadForMaster = {
        regionList: regionList,
        abmName: abmNameList,
        selectedCity:selectedCities,
        selectedState:selectedStates
      };

    } else {
     
const today = new Date();
const year = today.getFullYear();
const month = today.getMonth(); // 0-based, January is 0

// Determine financial year's start year
const startYear = month >= 3 ? year : year - 1;

// Calculate startDate: April 1st of financial year
const startDate = parseInt(`${startYear}0401`, 10);

// Calculate endDate: Last day of current month
const endYear = today.getFullYear();
const endMonth = today.getMonth() + 1; // 1-based, January is 1
const endDay = new Date(endYear, endMonth, 0).getDate();
const endDate = parseInt(`${endYear}${String(endMonth).padStart(2, '0')}${String(endDay).padStart(2, '0')}`, 10);

MonthlyToalOrdaringPayload = {
  regionList: 'EAST, WEST,NORTH,SOUTH 1,SOUTH 2', // default all regions
  startDate, // Calculated financial year start date
  endDate,   // Calculated current month end date
  brandList: '', // default case ""
  rsNameList: '', // default case ""
  abmName: '',
  retailerType: '',
  selectedMonths:'',
  selectedYears:'',
  selectedCity:'',
  selectedState:''

};

console.log(MonthlyToalOrdaringPayload);

      payloadForMaster = {
        regionList: '',
        abmName: '',
  selectedCity:'',
  selectedState:''
      };
      this.selectedAbmNames = [];
      this.selectedBrands = [];
      this.selectedRSNames = [];
      this.selectedRegions = [];
      this.selectedRetailerTypes = [];
      this.selectedMonths = [];
      this.selectedCities = [];
      this.selectedStates = [];
       this.selectedYears = [];

      if(this.dashboardService.selectedData === '4' || this.dashboardService.selectedData === '5' || this.dashboardService.selectedData === '6'){
        const today = new Date();
        const startDate = new Date(today.getFullYear(), today.getMonth(), 1);
        const endDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);
              const formatDate = (date: Date) => 
          date.getFullYear() * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
        //       GrowthOverPreviousMonthPayload = {
        //   startDate: formatDate(startDate),
        //   endDate: formatDate(endDate),
        //   // Add other fields if required
        // };
    
        // Set selected month
        const currentMonthId = today.getMonth() + 1;
        const currentMonth = this.availableMonths.find((month) => month.id === currentMonthId);
        this.selectedMonths = currentMonth ? [currentMonth] : [];
        MonthlyToalOrdaringPayload.startDate =  formatDate(startDate); /// default case start date of financial year in integer format
        MonthlyToalOrdaringPayload.endDate = formatDate(endDate);
      }

       

  //     if(['2','3'].indexOf(this.dashboardService.selectedData) != -1){

  //       this.selectedMonths = []; // Initialize selectedMonths array
  //   // Set selected months to the last 5 months from the current month
  //   const today = new Date();
  //   const currentMonthId = today.getMonth() + 1; // Get current month (1-12)
  // for (let i = 0; i < 5; i++) {
  //   const monthId = (currentMonthId - i - 1 + 12) % 12 + 1; // Handle wrapping around to the previous year
  //   const month = this.availableMonths.find((m) => m.id === monthId);
  //   if (month) {
  //     this.selectedMonths.push(month);
  //   }
  // }
  
  // // Ensure months are ordered from oldest to newest
  //    this.selectedMonths.reverse();
  //     const payload = this.generateFinancialYearPayload(this.selectedMonths);
  //     MonthlyToalOrdaringPayload.startDate =    payload.startDate, /// default case start date of financial year in integer format
  //     MonthlyToalOrdaringPayload.endDate = payload.endDate;
  // }
  
        const currentYear = today.getFullYear();
        const currentYearInList = this.yearsList.find((year) => year === currentYear);
        this.selectedYears = currentYearInList ? [currentYearInList] : [];
    }
    let GrowthOverPreviousMonthPayload;
    if (this.dashboardService.selectedData === '1') {
      if((data != 'search')){
          const today = new Date();
          const startDate = new Date(today.getFullYear(), today.getMonth(), 1);
          const endDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);
                const formatDate = (date: Date) => 
            date.getFullYear() * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
      
            
      
          // Set selected month
          const currentMonthId = today.getMonth() + 1;
          const currentMonth = this.availableMonths.find((month) => month.id === currentMonthId);
          this.selectedMonths = currentMonth ? [currentMonth] : [];
      
          // Set selected year
          const currentYear = today.getFullYear();
          const currentYearInList = this.yearsList.find((year) => year === currentYear);
          this.selectedYears = currentYearInList ? [currentYearInList] : [];
          const selectedMonths = this.selectedMonths.map((item) => item.id).join(', ');
          const selectedYears = this.selectedYears.map((item) => item).join(', ');
  
      //  // Prepare payload
      //  const GrowthOverPreviousMonthPayload: any = {
      //   startDate: formatDate(startDate),
      //   endDate: formatDate(endDate),
      //   selectedMonths:selectedMonths,
      //   selectedYears:selectedYears
      //   // Add other fields if required
      // };
          GrowthOverPreviousMonthPayload = {
            startDate: formatDate(startDate),
            endDate: formatDate(endDate),
            selectedMonths:selectedMonths,
            selectedYears:selectedYears,
            selectedCity:selectedCities,
            selectedState:selectedStates
            // Add other fields if required
          };
      }
      this.dashBoardInitalDataFn(data != 'search' ? GrowthOverPreviousMonthPayload : MonthlyToalOrdaringPayload);
      return;
    }
    this.GetMasterData(payloadForMaster, MonthlyToalOrdaringPayload);
  }

  isDropdownOpenForYear = false;
  selectedYears: number[] = [];
  filteredYearsList: number[] = [];
  yearsList: number[] = [];

  // Populate years dynamically
  initializeYears() {
    this.yearsList = [];
    this.selectedYears = [];
    const currentYear = new Date().getFullYear();
    for (let year = currentYear - 1; year <= currentYear; year++) {
      this.yearsList.push(year);
    }
    this.filteredYearsList = [...this.yearsList];
  }

  // Toggle dropdown visibility
  toggleDropdownVisibilityForYear() {
    // this.isDropdownOpenForYear = !this.isDropdownOpenForYear;
    this.toggleDropdownVisibility1('isDropdownOpenForYear');
    if (this.isDropdownOpenForYear) {
      this.filteredYearsList = this.filteredYearsList; // Reset filtered list on opening
    }
  }

  // Handle individual year selection
  toggleYearSelection(year: number) {
    const index = this.selectedYears.indexOf(year);
    if (index === -1) {
      this.selectedYears.push(year);
    } else {
      this.selectedYears.splice(index, 1);
    }
  }

  // Handle "Select All" functionality
  toggleAllYearSelection(event: any) {
    if (event.target.checked) {
      this.selectedYears = [...this.yearsList];
    } else {
      this.selectedYears = [];
    }
  }

  // Remove selected year
  removeSelectedYear(year: number, event: any) {
    event.stopPropagation();
    this.selectedYears = this.selectedYears.filter((y) => y !== year);
  }

  // Filter years based on search input
  searchInputValueForYear = '';
  filterAvailableYears() {
    this.filteredYearsList = this.yearsList.filter((year) => year.toString().includes(this.searchInputValueForYear.trim()));
  }

  // Check if all years are selected
  get areAllYearsSelected() {
    return this.yearsList.length > 0 && this.selectedYears.length === this.yearsList.length;
  }

  cards: any = [
    {
      background: 'eastBg',
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
      background: 'northBg',
      title: 'North',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      qtyValue: 'Loading...',
      qtyText: 'Loading...',
      number: 'Loading...',
      no: 'Loading...'
    },
    {
      background: 'south1Bg',
      title: 'South 1',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: 'Loading...',
      qtyValue: 'Loading...',
      qtyText: 'Loading...',
      no: 'Loading...'
    },
    {
      background: 'south2Bg',
      title: 'South 2',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: 'Loading...',
      no: 'Loading...',
      qtyValue: 'Loading...',
      qtyText: 'Loading...'
    },
    {
      background: 'westBg',
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
    const retailersData: any = [];
    const quantityData: any = [];
    const valueData: any = [];
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
        enabled: isMobile ? false : true,

        offsetX: -5, // X-axis offset
        style: {
          fontSize: '10px'
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
          const dataLabelOffsetsY = [0, 35, 0]; // Customize offsets per series index
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
        }
      },
      yaxis: [
        {
          title: {
            text: 'Quantity (K) Loading...', // Left y-axis title
            style: { 
              fontFamily:'unset'
           }
          },
          min: 0, // Minimum value for left y-axis
          labels: {
            show: false,
            formatter: function (val) {
              // return '' + val; // Format for value
              return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
            }
          },
          tickAmount: 4 // Adjust the number of ticks as necessary
        }
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
                return val; // Format for value
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
            retailersData.push(item.totalRetailerCode / 100);
            quantityData.push(item.totalQTY / 10000);
            valueData.push(item.totalRevenue / 10000000);
            //   retailersData.push((item.totalRetailerCode).toFixed(2));
            // quantityData.push((item.totalQTY /10000000).toFixed(2));
            // valueData.push((item.totalRevenue / 10000000).toFixed(2));

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });
         
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
                  zoom: true, // Enable zoom
                  zoomin: true, // Enable zoom-in
                  zoomout: true, // Enable zoom-out
                  pan: true, // Enable panning
                  reset: true // Reset zoom and pan to the initial state
                }
                // autoSelected: 'zoom'    // Set default tool to zoom
              }
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
              enabled: isMobile ? false : true,
              offsetX: -5, // X-axis offset
              style: {
                fontSize: '10px', // Set font size to 5px
                fontWeight: 'normal' // Remove bold styling by setting fontWeight to normal
              },
              background: {
                enabled: true,
                foreColor: '#000000'
              },
              // formatter: function (val: any, { seriesIndex, w }) {
              //   // Apply custom scaling to data labels based on series name
              //   const seriesName = w.config.series[seriesIndex].name;
              //   let scaledValue = val;

              //   // Apply custom scaling for each series
              //   if (seriesName === 'Quantity (k)') {
              //     scaledValue = (val * 10).toFixed(2); // Scale Quantity by 10
              //   } else if (seriesName === 'Retailers') {
              //     scaledValue = (val * 100).toFixed(0); // Scale Retailers by 100
              //   } else {
              //     scaledValue = val.toFixed(2); // No scaling for other series
              //   }

              //   // Offset Y based on series index
              //   const dataLabelOffsetsY = [0, 35, 0]; // Customize offsets per series index
              //   const offsetY = dataLabelOffsetsY[seriesIndex] || 0; // Use the default offset if none provided

              //   // Update the offsetY dynamically
              //   w.config.dataLabels.offsetX = offsetY;
              //   return scaledValue;
              // }

              formatter: function (val: any, { seriesIndex, w, dataPointIndex }) {
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
              
                // // Offset Y based on series index, customize for last data point
                // const dataLabelOffsetsY = [0, 35, 0]; // Default offsets per series index
                // const isLastPoint = dataPointIndex === w.config.series[seriesIndex].data.length - 1;
              
                // // Adjust offset dynamically for the last data point
                // const offsetY = isLastPoint && seriesIndex === 1 ? -35 : dataLabelOffsetsY[seriesIndex] || 0;


  // Default offsets per series index
  const dataLabelOffsetsY = [0, 35, 0]; 
  let offsetY;

  // Adjust offset dynamically based on conditions
  if (seriesIndex === 1 && dataPointIndex === w.config.series[seriesIndex].data.length - 1) {
    offsetY = -35; // Last data point for series index 1
  } else if (seriesIndex === 2 && dataPointIndex === 0) {
    offsetY = 50; // First data point for series index 2
  } else {
    offsetY = dataLabelOffsetsY[seriesIndex] || 0; // Default offsets
  }

              
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
                 fontFamily:'unset'
                }
              },
              min: 0,
              labels: {
                formatter: function (val) {
                  return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
                },
                // offsetX: -0
              },
              tickAmount: 3
            },
            tooltip: {
              shared: true,
              // intersect: true,
              y: {
                formatter: function (value, { series, seriesIndex,dataPointIndex }) {
                  // Apply scaling based on series name in the tooltip
                  const seriesName = series[seriesIndex].name;
                  
                  if (seriesIndex === 0) {
                    return (value * 10).toFixed(2); // Scale Quantity by 10
                  } else if (seriesIndex === 1) {
                    return (value * 100).toFixed(0); // Scale Retailers by 100
                  }
                  return value.toFixed(2); // No scaling for other series
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
            zoom: true, // Enable zoom
            zoomin: true, // Enable zoom-in
            zoomout: true, // Enable zoom-out
            pan: true, // Enable panning
            reset: true // Reset zoom and pan to the initial state
          },
          autoSelected: 'zoom' // Set default tool to zoom
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
      // dataLabels: {
      //   enabled: this.isMobileView ? false: true,
      //   background: {
      //     enabled: true,
      //     foreColor: '#000000'
      //   },
      // },
      dataLabels: {
        enabled: this.isMobileView ? false : true,
        style: {
          colors: ['#000000'] // Set the color of the data labels to black
        },
        background: {
          enabled: true,
          foreColor: '#000000' // This controls the color of the text in the background
        }
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
        }
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
              fontFamily:'unset'
           }
          },
          // min: 0, // Minimum value for left y-axis
          labels: {
            show: false,
            formatter: function (val: any) {
              return '' + val; // Format for value
            }
          },
          tickAmount: 1 // Adjust the number of ticks as necessary
        }
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
          // this.RegionWiseMonthlyDistribution(MonthlyToalOrdaringPayload);
this.GrowthOverPreviousYear(MonthlyToalOrdaringPayload);
          response.body.forEach((item: any) => {
            // Push values to each series array
            // retailersData.push(item.retailerGrowth);
            // quantityData.push(item.orderGrowth);
            // valueData.push(item.priceGrowth);
            // retailersData.push(item.retailerGrowth / 6);
            // quantityData.push((item.orderGrowth / 1000).toFixed(2)); // Converts orderGrowth to thousands
            // valueData.push((item.priceGrowth / 10000000).toFixed(2)); // Converts priceGrowth to crores
            //   retailersData.push(item.retailerGrowthPercentage);
            // quantityData.push((item.orderQtyGrowthPercentage).toFixed(2)); // Converts orderGrowth to thousands
            // valueData.push((item.priceGrowthPercentage).toFixed(2)); // Converts priceGrowth to crores
            

            retailersData.push(item.retailerGrowth / 6);
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
              height: 700,
              toolbar: {
                show: true,
                tools: {
                  zoom: true, // Enable zoom
                  zoomin: true, // Enable zoom-in
                  zoomout: true, // Enable zoom-out
                  pan: true, // Enable panning
                  reset: true // Reset zoom and pan to the initial state
                }
              }
            },
            colors: [
              '#007bff',
        '#28a745',
        '#ffc107', 
        '#dc3545', 
        '#ff5733', 
            ],
            title: {
              text: 'Growth Over Previous Month', // Chart title
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
              }
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
              enabled: this.isMobileView ? false : true,
              style: {
                colors: ['#000000'],
                fontWeight: 'normal' // Set the color of the data labels to black
              },
              formatter: function (val: any, { seriesIndex }) {
                // if (seriesIndex === 0) {
                //   val = (val * 6) + ' %'; // Multiply the value by 6 for retailer values
                // }

                if (seriesIndex === 0) {
                  val = val * 6;
                  // return   val = (val).toFixed(2) + ' %'; // Multiply by 6 and format to 2 decimal places
                }
                return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
              }
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
                  style: {
                    fontFamily: 'unset'
                  },
                  offsetX: 5
                },
                tickAmount: 10, // Adjust the number of ticks based on the range
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
                formatter: function (val:any, { seriesIndex }) {
                  if (seriesIndex === 0) {
                    val = val * 6; // Multiply the value by 6 for retailer values
                    // return   val = (val).toFixed(2) + ' %'; // Multiply by 6 and format to 2 decimal places
                  }
                  
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
      this.isDropdownOpenForYear = false;
      this.isDropdownOpenForState = false;
      this.isDropdownOpenForCity =false;
      this.isDropdownOpenForSelectionType = false;
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
    const FilterRSNames = this.availableRSNames.filter(
      (data: any) => data.region && this.selectedAbmNames.some((selectedAbm: any) => selectedAbm.name === data.region)
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
    if(this.selectedRSNames.length ==0){
      return false;
    }
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
    if(this.selectedBrands.length == 0){
     return false;
    }
    return this.selectedBrands.length === this.availableBrands.length; // Return true if all Brands are selected
  }

  GetMasterData(payload: any, MonthlyToalOrdaringPayload?: any, isFromFilter?: any): void {
    // this.spinner.show();
    this.dashboardService.GetMasterData(payload).subscribe(
      (response: any) => {
        // this.spinner.hide();
        if (!isFromFilter) {
          if (this.dashboardService.selectedData === '2') {
            this.MonthlyToalOrdaring(MonthlyToalOrdaringPayload);
          } else if (this.dashboardService.selectedData === '3') {
            this.chartOptionslineForOrdBhFn(MonthlyToalOrdaringPayload);
          } else if (this.dashboardService.selectedData === '4') {
            this.percentageOfOrdersOfDayInMonth(MonthlyToalOrdaringPayload);
            delete MonthlyToalOrdaringPayload.selectedCity;
            delete MonthlyToalOrdaringPayload.selectedState;            
          } else if (this.dashboardService.selectedData === '5') {
            this.selectedSelectionTypeForUI = this.selectedSelectionType;
            if(this.selectedSelectionTypeForUI === 'Order Wise'){
              this.topSKUOrderedOverall(MonthlyToalOrdaringPayload);
            } else {
              // this.topSKUOrderedOverallpriceWiseFn(MonthlyToalOrdaringPayload);
            }
          }
          else if (this.dashboardService.selectedData === '6') {
            this.selectedSelectionTypeForUI = this.selectedSelectionType;
            if(this.selectedSelectionTypeForUI === 'Order Wise'){
              this.topRetailersOverallcount(MonthlyToalOrdaringPayload);
            } else {
              this.topRetailersOverallsum(MonthlyToalOrdaringPayload);
            }
          }
        }

        if (response && response.body && response.body.region && response.body.region.length > 0) {
          const availableRegions = response.body.region.map((region: any, index: any) => {
            return {
              id: index + 1, // Assign unique IDs starting from 1
              name: region.replace(' ', '') // Replace space in 'SOUTH 1' and 'SOUTH 2' with empty string
            };
          });
          // this.availableRegions = availableRegions;
        } else {
          // this.availableRegions = [];
        }

        if (response && response.body && response.body.brand && response.body.brand.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableBrands: Brand[] = response.body.brand.map(([id, name]: [string, string]) => {
            return {
              id: parseInt(id), // Convert ID to a number
              name: name // Keep the name as is
            };
          });
          this.availableBrands = availableBrands;
        } else {
          this.availableBrands = [];
        }

        if (response && response.body && response.body.rsName && response.body.rsName.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableRSNames: Brand[] = response.body.rsName.map(([name, userid, region]: [string, string, string, string]) => {
            return {
              id: userid, // Convert ID to a number
              name: name,
              region: region
              // Keep the name as is
            };
          });
          this.availableRSNames = availableRSNames;
        } else {
          this.availableRSNames = [];
        }
        
        if (response && response.body && response.body.abmName && response.body.abmName.length > 0) {
          // Step 3: Transform the data with type assertion
          const availableAbmNames: Brand[] = response.body.abmName.map(([id, name, region]: [string, string, string]) => {
            return {
              id: id, // Convert ID to a number
              name: name, // Keep the name as is
              region: region
            };
          });
          this.availableAbmNames = availableAbmNames;
        }else {
          this.availableAbmNames = [];
        }

        if (response && response.body && response.body.city && response.body.city.length > 0) {
          // Step 3: Transform the data with type assertion
          // const availableAbmNames: Brand[] = response.body.abmName.map(([id, name, region]: [string, string, string]) => {
          //   return {
          //     id: id, // Convert ID to a number
          //     name: name, // Keep the name as is
          //     region: region
          //   };
          // });

          this.availableCities = response.body.city;
        }else {
          this.availableCities = [];
        }

        if (response && response.body && response.body.state && response.body.state.length > 0) {
          // Step 3: Transform the data with type assertion
          // const availableAbmNames: Brand[] = response.body.abmName.map(([id, name, region]: [string, string, string]) => {
          //   return {
          //     id: id, // Convert ID to a number
          //     name: name, // Keep the name as is
          //     region: region
          //   };
          // });

          this.availableStates = response.body.state;
        }else {
          this.availableStates = [];
        }

        
      },
      (error) => {
        // this.errorMessage = 'An error occurred during login';
        console.error('Login error:', error);
      }
    );
  }

  // this is for getting the cards data during the intial loading.
  public dashBoardInitalDataFn = async (data?: any) => {
    this.spinner.show();
    for (let i = 0; i < this.cards.length; i++) {
      this.cards[i].number = 'Loading';
      this.cards[i].qtyValue = 'Loading';
      this.cards[i].dealercount = 'Loading';
      this.cards[i].no = 'Loading';
    }

    this.currentMonthToDateText = 'Loading...';
    this.dashboardService.dashBoardInitalData(data).subscribe(
      (response) => {
        this.spinner.hide();

        if (response && response.body && response.body.length > 0) {

this.getLastUpdatedRecordDate = response.body &&response.body[0] &&  response.body[0].endDate;
this.currentMonthToDateText = this.prepareOrderMessage(this.selectedYears, this.selectedMonths);

          // Map response data to the cards
          // for (let i = 0; i < this.cards.length; i++) {
          //   const cardData = response.body[i] || {};
          //   this.cards[i].number = cardData.totalOrder || 0;
          //   this.cards[i].qtyValue = cardData.orderValue ? (cardData.orderValue / 10000000).toFixed(2) + 'Cr' : '0.00Cr';
          //   this.cards[i].dealercount = cardData.delears || 0;
          //   this.cards[i].no = cardData.orderQuentity ? (cardData.orderQuentity / 1000).toFixed(2) + 'K' : '0.00K';
          // }


          // Assuming the response data is stored in 'response.body' and the cards array is initialized

// Initialize sums for each region
let totalOrderSum :any= { 'EAST': 0, 'NORTH': 0, 'SOUTH 1': 0, 'SOUTH 2': 0, 'WEST': 0 };
let orderValueSum :any= { 'EAST': 0, 'NORTH': 0, 'SOUTH 1': 0, 'SOUTH 2': 0, 'WEST': 0 };
let dealerCountSum :any= { 'EAST': 0, 'NORTH': 0, 'SOUTH 1': 0, 'SOUTH 2': 0, 'WEST': 0 };
let orderQuantitySum :any= { 'EAST': 0, 'NORTH': 0, 'SOUTH 1': 0, 'SOUTH 2': 0, 'WEST': 0 };

// Loop through the response and sum values based on region
for (const cardData of response.body) {
  const region = cardData.region;
  
  if (totalOrderSum.hasOwnProperty(region)) {
    totalOrderSum[region] += cardData.totalOrder || 0;
    orderValueSum[region] += cardData.orderValue || 0;
    dealerCountSum[region] += cardData.delears || 0;
    orderQuantitySum[region] += cardData.orderQuentity || 0;
  }
}

// Now, assign summed values to the corresponding card indexes (0 - 4)
for (let i = 0; i < this.cards.length; i++) {
  const region = ['EAST', 'NORTH', 'SOUTH 1', 'SOUTH 2', 'WEST'][i]; // Mapping regions to indexes 0-4

  if (this.cards[i]) {
    this.cards[i].number = totalOrderSum[region] || 0;
    this.cards[i].qtyValue = orderValueSum[region] ? (orderValueSum[region] / 10000000).toFixed(2) + ' Crs' : '0.00Crs';
    this.cards[i].dealercount = dealerCountSum[region] || 0;
    this.cards[i].no = orderQuantitySum[region] ? (orderQuantitySum[region] / 1000).toFixed(2) + ' K' : '0.00K';
  }
}

        } else {
          // Fallback to default empty data
          for (let i = 0; i < this.cards.length; i++) {
            this.cards[i].number = '';
            this.cards[i].qtyValue = '';
            this.cards[i].dealercount = '';
            this.cards[i].no = '';
          }
          this.currentMonthToDateText = 'No Data Available';
        }

        console.log('Data processed successfully');
      },
      (error) => {
        this.spinner.hide();
        console.error('Error fetching dashboard data:', error);

        // Optional: Set default empty data on error as well
        for (let i = 0; i < this.cards.length; i++) {
          this.cards[i].number = '';
          this.cards[i].qtyValue = '';
          this.cards[i].dealercount = '';
          this.cards[i].no = '';

          // this.cards[i].number = 0;
          // this.cards[i].qtyValue = '0.00Cr';
          // this.cards[i].dealercount = 0;
          // this.cards[i].no = '0.00K';
        }
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
              enabled: this.isMobileView ? false : true
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
let lastHoveredSeriesIndex:any = '';
let lastHoveredDataPointIndex:any = '';

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
        text: 'Regionwise Month and Month Growth Loading...',
        align: 'center'
        // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
      },
      yaxis: [
        {
          title: {
            text: '(Quantity) Loading...',
            style: { 
              fontFamily:'unset'
           }
          },
          labels: {
            show: false,
            formatter: (val: any) => '' + val
          },
          tickAmount: 4
        }
      ],
      xaxis: {
        labels: { trim: false, show: true },
        categories: [],
        title: { style: { color: '#000000' } }
      },
      dataLabels: {
        //  enabled: true ,
        enabled: this.isMobileView ? false : true,

        style: {
          fontSize: '10px',
          fontWeight: 'normal' // Remove bold styling by setting fontWeight to normal,
        },
        background: {
          // enabled: true,
          foreColor: '#000000'
        },
        formatter: (value: any, { seriesIndex, dataPointIndex, w }: any) => {
          let adjustedValue = value;
  
          // Adjust tooltip values based on the series group
          if (w.config.series[seriesIndex].group === 'retailers') {
            // Multiply retailer code by 100 in tooltip
            adjustedValue = (value * 100).toFixed(0);
          } else if (w.config.series[seriesIndex].group === 'qty') {
            // Divide quantity by 100 for tooltip display
            adjustedValue = ((value *10000)/ 1000).toFixed(2);
          }
  
          // Return formatted value with 2 decimal places
          return adjustedValue;
        }
      },
      // dataLabels: {formatter : (_, opt:any) => {
      //   const name = opt.w.globals.labels[opt.dataPointIndex];
      //   const val = [opt.dataPointIndex];
      //   return val ? `${val}` : '';
      // },},
      chart: {
        type: 'bar',
        height: 1000,
        stacked: true,
        events: {
          mouseMove: (event, chartContext, config) => {
            const seriesIndex = config.seriesIndex;
            const dataPointIndex = config.dataPointIndex;
            console.log('Mouse move event captured:', { seriesIndex, dataPointIndex });
    
            // Store the seriesIndex and dataPointIndex for later use
            lastHoveredSeriesIndex = seriesIndex;
            lastHoveredDataPointIndex = dataPointIndex;
          }
        }
      },
      plotOptions: { bar: { horizontal: false } },
      fill: { opacity: 1 },
      // colors: [
      //   '#80c7fd',
      //   '#008FFB',
      //   '#80f1cb',
      //   '#00E396',
      //   '#feb019', // Retailers colors
      //   '#FF5733',
      //   '#FFBD33',
      //   '#C70039',
      //   '#900C3F',
      //   '#581845', // Qty colors
      //   '#2ECC71',
      //   '#28B463',
      //   '#239B56',
      //   '#1D8348',
      //   '#186A3B' // Value colors
      // ],
      colors: [
        '#007bff',
        '#28a745',
        '#ffc107', 
        '#dc3545', 
        '#ff5733', // Blue, red-orange, green, amber, crimson
        // '#6f42c1',
        // '#17a2b8',
        // '#fd7e14',
        // '#6610f2',
        // '#e83e8c', // Purple, teal, orange, violet, pink
        // '#20c997',
        // '#ff6347',
        // '#8b0000',
        // '#4169e1',
        // '#32cd32' // Aqua, tomato, dark red, royal blue, lime green
      ],
      legend: { position: isMobile ? 'top' : 'bottom', horizontalAlign: 'center' },
      
   
  }
  
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

                const  selectedMoths = this.selectedMonths;
                // Prepare data series for each metric and region
                const prepareSeriesData = (data: any[], metric: string, group: string) => {
                    // const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
                    const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];

                    const metricLabels: { [key: string]: string } = {
                        totalRetailerCode: 'Total Retailers',
                        totalQTY: 'Total Qty (K)',
                        totalRevenue: 'Total Value (Cr)'
                    };

                    return regions.map(region => {
                        const regionData = data.filter(item => item.region === region);
                        return {
                            // name: `${metricLabels[metric]} - ${region}`,
                            name: `${region}`,
                            group: group,
                            data: regionData.map(item => {
                                if (metric === 'totalQTY') return (item.totalQTY / 10000).toFixed(2); // Adjust units
                                if (metric === 'totalRevenue') return (item.totalRevenue / 10000000).toFixed(2) // Adjust units
                                if (metric === 'totalRetailerCode') return (item.totalRetailerCode / 100).toFixed(2) // Adjust units
                                return item;
                            })
                        };
                    });
                };

                // const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
                const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];

                // Update chart options with dynamic series and categories
                this.RegionWiseMonthlyDistibutionOptions = {
                    ...this.RegionWiseMonthlyDistibutionOptions,
                    series: [
                        // ...prepareSeriesData(response.body, 'totalRetailerCode', 'retailers'),
                        // ...prepareSeriesData(response.body, 'totalQTY', 'qty'),
                        ...prepareSeriesData(response.body, 'totalRevenue', 'value')
                    ],
                    xaxis: { ...this.RegionWiseMonthlyDistibutionOptions.xaxis, categories },
                    title: {
                      text: 'Regionwise Month and Month Growth',
                      align: 'center',
                      // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
                  },
                  yaxis: [{
                    title: {
                        text: 'Value (Cr)',
                        style: { 
                          fontFamily:'unset'
                       },
                        offsetX:10
                    },
                    labels: {
                        formatter: (val: any) => '' + val
                    },
                    tickAmount: 10
                }],
             // Regions mapping

tooltip: {
    enabled: true,
    custom: function({ series, seriesIndex, dataPointIndex, w }) {
        // Find the region name based on seriesIndex
        const regionName = regions[seriesIndex];

        // Find the month corresponding to dataPointIndex (e.g., dataPointIndex 0 = month 4)
     let month =  dataPointIndex + 4;

     if(selectedMoths &&  selectedMoths.length > 0 ){
      // Find the earliest month and year
      const ascendingOrderData = selectedMoths.sort((a:any, b:any) => {
       return a.id - b.id;
     });
     
     if(ascendingOrderData){
       const monthsData= [
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

    const getFiltedData = monthsData.filter(
      month => 
        month.id >= ascendingOrderData[0].id && 
        month.id <= ascendingOrderData[ascendingOrderData.length - 1].id
    );
  
      month = getFiltedData[dataPointIndex].id;
     }
      
     }

        // Filter the response array to find the matching record
        const record = response.body.find(
            (item:any) => item.region === regionName && item.month === month
        );


        // Build the tooltip content
        let tooltipContent = `
            <div style="padding: 8px; font-size: 12px; background: white; border: 1px solid #ddd;">
                <strong>${regionName} Region</strong>
        `;

        if (record) {
            // Add data if record is found
            // tooltipContent += `
            //     <div>Retailers: ${record.totalRetailerCode}</div>
            //     <div>Qty (K): ${record.totalQTY/1000}</div>
            //     <div>Value (Cr): ${record.totalRevenue/10000000}</div>
            // `;
            tooltipContent += `
             <div>Value (Cr): ${(record.totalRevenue / 10000000).toFixed(2)}</div>
             <div>Qty (K): ${(record.totalQTY / 1000).toFixed(2)}</div>
             <div>Retailers: ${record.totalRetailerCode}</div>`;

        } else {
            // Fallback content if no record is found
            tooltipContent += `<div>No data available</div>`;
        }

        tooltipContent += `</div>`;
        return tooltipContent;
    }
}
,
dataLabels: {
  enabled: true,
  formatter: function(value, { seriesIndex, dataPointIndex }) {
      const regionName = regions[seriesIndex];
      let month = dataPointIndex + 4;
    
      if(selectedMoths &&  selectedMoths.length > 0 ){
        // Find the earliest month and year
        const ascendingOrderData = selectedMoths.sort((a:any, b:any) => {
         return a.id - b.id;
       });
       
       if(ascendingOrderData){
         const monthsData= [
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

      const getFiltedData = monthsData.filter(
        month => 
          month.id >= ascendingOrderData[0].id && 
          month.id <= ascendingOrderData[ascendingOrderData.length - 1].id
      );
    
        month = getFiltedData[dataPointIndex].id;
       }
        
       }



       const record = response.body.find(
        (item: any) => item.region === regionName && item.month === month
    );


      if (record) {
          return [
              `Value: ${(record.totalRevenue / 10000000).toFixed(2)} Cr`,
              `Qty: ${(record.totalQTY / 1000).toFixed(2)} K`,
              `Retailers: ${record.totalRetailerCode}`
          ];
      } else {
          return ["No Data"];
      }
  },
  style: {
      fontSize: '10px',  // Set font size to fit inside the box
      fontFamily: 'Arial, sans-serif',  // Font family
      fontWeight: 'normal',  // Font weight
      colors: ['#ffffff'],  // Font color (white)
      align:'left',
  },
  background: {
    enabled: false,
    //foreColor: '#000000',  // Black text for high contrast
   // borderRadius: 5,
   // padding: 5,
   // opacity: 0.9,  // Higher opacity for solid background
   // borderWidth: 1,
   // borderColor: '#007BFF',  // Border with a vibrant blue
},
  offsetY:30
}




                };

            }
        },
        (error) => {
            this.spinner.hide();
            console.error('Error fetching region-wise monthly distribution data:', error);
        }
    );

    // this.dashboardService.RegionWiseMonthlyDistribution(MonthlyTotalOrderingPayload).subscribe(
    //   (response) => {
    //       if (response && response.body) {
    //           this.spinner.hide();
    //           this.RegionWiseGrowthOverPreviousMonth(MonthlyTotalOrderingPayload);

    //           // Prepare categories based on unique months
    //           const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
    //           const categories: string[] = [];
    //           uniqueMonths.forEach((month: any) => {
    //               categories.push(monthNames[month - 1]);
    //           });

    //           // Prepare data series for each metric and region
    //           const prepareSeriesData = (data: any[], metric: string, group: string) => {
    //               const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
    //               const metricLabels: { [key: string]: string } = {
    //                   totalRetailerCode: 'Total Retailers',
    //                   totalQTY: 'Total Qty (K)',
    //                   totalRevenue: 'Total Value (Cr)'
    //               };

    //               return regions.map(region => {
    //                   const regionData = data.filter(item => item.region === region);
    //                   return {
    //                       name: `${metricLabels[metric]} - ${region}`,
    //                       group: group,
    //                       data: regionData.map(item => {
    //                           if (metric === 'totalQTY') return (item.totalQTY / 1000).toFixed(2); // Adjust units
    //                           if (metric === 'totalRevenue') return (item.totalRevenue / 10000000).toFixed(2); // Adjust units
    //                           return item.totalRetailerCode;
    //                       })
    //                   };
    //               });
    //           };

    //           // Update chart options with dynamic series and categories
    //           const tooltipData = response.body; // Store response data for tooltip logic
    //           this.RegionWiseMonthlyDistibutionOptions = {
    //               ...this.RegionWiseMonthlyDistibutionOptions,
    //               series: [
    //                   // Prepare series dynamically based on metrics
    //                   // ...prepareSeriesData(response.body, 'totalRetailerCode', 'retailers'),
    //                   // ...prepareSeriesData(response.body, 'totalQTY', 'qty'),
    //                   ...prepareSeriesData(response.body, 'totalRevenue', 'value')
    //               ],
    //               xaxis: {
    //                   ...this.RegionWiseMonthlyDistibutionOptions.xaxis,
    //                   categories
    //               },
    //               title: {
    //                   text: 'Region Wise Distribution',
    //                   align: 'center',
    //               },
    //               yaxis: [{
    //                   title: {
    //                       text: '(Quantity)',
    //                       style: { color: '#000000' }
    //                   },
    //                   labels: {
    //                       formatter: (val: any) => '' + val
    //                   },
    //                   tickAmount: 4
    //               }],
    //               tooltip: {
    //                   shared: true,
    //                   intersect: false,
    //                   custom: ({ series, seriesIndex, dataPointIndex, w }) => {
    //                       const dataPoint = tooltipData[dataPointIndex] || {}; // Get data for the current index
    //                       const totalOrders = dataPoint.totalRetailerCode || 0;
    //                       const totalQty = (dataPoint.totalQTY || 0) / 1000; // Convert to K
    //                       const totalValue = (dataPoint.totalRevenue || 0) / 10000000; // Convert to Cr

    //                       return `
    //                           <div style="padding: 10px; border-radius: 5px; background-color: #f4f4f4; color: #333;">
    //                               <strong>Orders:</strong> ${totalOrders}<br>
    //                               <strong>Qty (K):</strong> ${totalQty.toFixed(2)}<br>
    //                               <strong>Value (Cr):</strong> ${totalValue.toFixed(2)}
    //                           </div>
    //                       `;
    //                   }
    //               }
    //           };
    //       }
    //   },
    //   (error) => {
    //       this.spinner.hide();
    //       console.error('Error fetching region-wise monthly distribution data:', error);
    //   }
    // );
    // this.dashboardService.RegionWiseMonthlyDistribution(MonthlyTotalOrderingPayload).subscribe(
    //   (response) => {
    //     if (response && response.body) {
    //       this.spinner.hide();
    //       this.RegionWiseGrowthOverPreviousMonth(MonthlyTotalOrderingPayload);

    //       // Prepare categories based on unique months
    //       const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
    //       const categories: string[] = [];
    //       uniqueMonths.forEach((month: any) => {
    //         categories.push(monthNames[month - 1]);
    //       });

    //       // Prepare data series for each metric and region
    //       const prepareSeriesData = (data: any[], metric: string) => {
    //         const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
    //         const metricLabels: { [key: string]: string } = {
    //           totalRetailerCode: 'Total Retailers',
    //           totalQTY: 'Total Qty (K)',
    //           totalRevenue: 'Total Value (Cr)'
    //         };

    //         return regions.map((region) => {
    //           const regionData = data.filter((item) => item.region === region);
    //           return {
    //             name: `${metricLabels[metric]} - ${region}`, // Ensure region info is in 'name'
    //             data: regionData.map((item) => {
    //               if (metric === 'totalQTY') return (item.totalQTY / 1000).toFixed(2); // Adjust units
    //               if (metric === 'totalRevenue') return (item.totalRevenue / 10000000).toFixed(2); // Adjust units
    //               return item.totalRetailerCode;
    //             }),
    //             region: region // Adding region to the data structure
    //           };
    //         });
    //       };

    //       const tooltipData = response.body; // Store response data for tooltip logic
    //       const seriesData = prepareSeriesData(response.body, 'totalRevenue'); // Adjust metric if needed

    //       // Update chart options with dynamic series and categories
    //       this.RegionWiseMonthlyDistibutionOptions = {
    //         ...this.RegionWiseMonthlyDistibutionOptions,
    //         series: [
    //                             ...prepareSeriesData(response.body, 'totalRetailerCode', 'retailers'),
    //                             ...prepareSeriesData(response.body, 'totalQTY', 'qty'),
    //                             ...prepareSeriesData(response.body, 'totalRevenue', 'value')
    //                         ],
            
    //         xaxis: {
    //           ...this.RegionWiseMonthlyDistibutionOptions.xaxis,
    //           categories
    //         },
    //         title: {
    //           text: 'Region Wise Distribution',
    //           align: 'center'
    //         },
    //         yaxis: [
    //           {
    //             title: {
    //               text: '(Quantity)',
    //               style: { color: '#000000' }
    //             },
    //             labels: {
    //               formatter: (val: any) => '' + val
    //             },
    //             tickAmount: 4
    //           }
    //         ]
    //         // tooltip: {
    //         //   shared: true,
    //         //   intersect: false,

    //         //   custom: ({ series, seriesIndex, dataPointIndex, w }) => {
    //         //     const seriesName = series[seriesIndex]?.name || '';
    //         //     const region = seriesName.split(' - ')[1];

    //         //     // Get the data point for this region
    //         //     const dataPoint = tooltipData.find(
    //         //       (item:any) => item.region === region && item.month === uniqueMonths[dataPointIndex]
    //         //     ) || {};

    //         //     const totalOrders = dataPoint.totalRetailerCode || 0;
    //         //     const totalQty = (dataPoint.totalQTY || 0) / 1000;
    //         //     const totalValue = (dataPoint.totalRevenue || 0) / 10000000;

    //         //     return `
    //         //       <div style="padding: 10px; border-radius: 5px; background-color: #f4f4f4; color: #333;">
    //         //         <strong>Region:</strong> ${region}<br>
    //         //         <strong>Orders:</strong> ${totalOrders}<br>
    //         //         <strong>Qty (K):</strong> ${totalQty.toFixed(2)}<br>
    //         //         <strong>Value (Cr):</strong> ${totalValue.toFixed(2)}
    //         //       </div>
    //         //     `;
    //         //   }
    //         // }
    //       };
    //     }
    //   },
    //   (error) => {
    //     this.spinner.hide();
    //     console.error('Error fetching region-wise monthly distribution data:', error);
    //   }
    // );
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
        text: 'Region Wise Growth Over Previous Month Loading...',
        align: 'center'
        // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
      },
      yaxis: [
        {
          title: {
            text: '(Growth Value)',
            style: { 
              fontFamily:'unset'
           }
          },
          labels: {
            show: false,
            formatter: (val: any) => '' + val
          },
          tickAmount: 4
        }
      ],
      xaxis: {
        labels: { trim: false, show: true },
        categories: [],
        title: { style: { color: '#000000' } }
      },
      dataLabels: {
        //  enabled: true,
        enabled: this.isMobileView ? false : true,

        style: {
          fontSize: '10px',
          colors: ['#000000'],
          fontWeight: 'normal' // Remove bold styling by setting fontWeight to normal
        }
      },
      chart: {
        type: 'bar',
        height: 1000,
        stacked: true
      },
      plotOptions: { bar: { horizontal: false } },
      fill: { opacity: 1 },
      // colors: [
      //     '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019',
      //     '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845',
      //     '#2ECC71', '#28B463', '#239B56', '#1D8348', '#186A3B'
      // ],t
      colors: [
        '#007bff',
        '#28a745',
        '#ffc107', 
        '#dc3545', 
        '#ff5733',
        // Blue, red-orange, green, amber, crimson
        // '#6f42c1',
        // '#17a2b8',
        // '#fd7e14',
        // '#6610f2',
        // '#e83e8c', // Purple, teal, orange, violet, pink
        // '#20c997',
        // '#ff6347',
        // '#8b0000',
        // '#4169e1',
        // '#32cd32' // Aqua, tomato, dark red, royal blue, lime green
      ],
      legend: { position: isMobile ? 'top' : 'bottom', horizontalAlign: 'center' }
    };

    this.dashboardService.RegionWiseGrowthOverPreviousMonth(MonthlyToalOrdaringPayload).subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();
this.regionwiseGrowthOverPreviousYearMonthly(MonthlyToalOrdaringPayload);
          // Prepare categories based on unique months
          const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
          uniqueMonths.forEach((month: any) => {
            categories.push(monthNames[month - 1]);
          });

          // Function to prepare series data
          // const prepareSeriesData = (data: any[], metric: string, group: string) => {
          //   const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
          //   const metricLabels: { [key: string]: string } = {
          //     retailerGrowth: 'Retailer Growth',
          //     orderGrowth: 'Order Growth (K)',
          //     priceGrowth: 'Price Growth (Cr)'
          //   };

          //   return regions.map((region) => {
          //     const regionData = data.filter((item) => item.region === region);
          //     return {
          //       name: `${metricLabels[metric]} - ${region}`,
          //       group: group,
          //       data: regionData.map((item) => {
          //         if (metric === 'orderGrowth') return (item.orderGrowth / 1000).toFixed(2); // Adjust units
          //         if (metric === 'priceGrowth') return ((item.priceGrowth / 10000000).toFixed(2)); // Adjust units
          //         return item.retailerGrowth;
          //       })
          //     };
          //   });
          // };

          const prepareSeriesData = (data: any[], metric: string, group: string) => {
            const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
            const metricLabels: { [key: string]: string } = {
              retailerGrowth: 'Retailer Growth',
              orderGrowth: 'Order Growth (K)',
              priceGrowth: 'Price Growth (Cr)'
            };
          
            return regions.map((region) => {
              const regionData = data.filter((item) => item.region.toUpperCase() === region.toUpperCase()); // Case-insensitive comparison
              return {
                name: `${metricLabels[metric]} - ${region}`,
                group: group,
                data: regionData.map((item) => {
                  if (metric === 'orderGrowth') return (item.orderGrowth / 1000).toFixed(2); // Adjust units
                  if (metric === 'priceGrowth') return ((item.priceGrowth / 10000000).toFixed(2)); // Adjust units
                  
                  return item.retailerGrowth;
                })
              };
            });
          };
          
               const selectedMoths = this.selectedMonths;

          const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];

          // Update chart options with dynamic series and categories
          this.RegionWiseGrowthOverPreviousMonthOptions = {
            ...this.RegionWiseGrowthOverPreviousMonthOptions,
            series: [
              // ...prepareSeriesData(response.body, 'retailerGrowth', 'retailers'),
              // ...prepareSeriesData(response.body, 'orderGrowth', 'qty'),
              ...prepareSeriesData(response.body, 'priceGrowth', 'value')
            ],
            xaxis: { ...this.RegionWiseGrowthOverPreviousMonthOptions.xaxis, categories },
            title: {
              text: 'Region Wise Growth Over Previous Month',
              align: 'center'
              // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
            },
            yaxis: [
              {
                title: {
                  text: 'Price (Crs)',
                  style: { 
                    fontFamily:'unset'
                 },
                  offsetX:20
                },
                labels: {
                  formatter: (val: any) => '' + val
                },
                tickAmount: 10,
              }
            ],

          //   tooltip: {
          //     enabled: true,
          //     custom: function({ series, seriesIndex, dataPointIndex, w }) {
          //         // Find the region name based on seriesIndex
          //         const regionName = regions[seriesIndex];
          
          //         // Find the month corresponding to dataPointIndex (e.g., dataPointIndex 0 = month 4)
          //         const month = dataPointIndex + 4;
          
          //         // Filter the response array to find the matching record
          //         const record = response.body.find(
          //             (item:any) => item.region === regionName && item.month === month
          //         );
          
          //         // Build the tooltip content
          //         let tooltipContent = `
          //             <div style="padding: 8px; font-size: 12px; background: white; border: 1px solid #ddd;">
          //                 <strong>${regionName} Region</strong>
          //         `;
          
          //         if (record) {
          //             // Add data if record is found
          //             // tooltipContent += `
          //             //     <div>Retailers: ${record.totalRetailerCode}</div>
          //             //     <div>Qty (K): ${record.totalQTY/1000}</div>
          //             //     <div>Value (Cr): ${record.totalRevenue/10000000}</div>
          //             // `;


                      
          //             tooltipContent += `
          //     <div>RetailerGrowth: ${record.retailerGrowth}</div>
          //     <div>OrderGrowth (K): ${(record.orderGrowth / 1000).toFixed(2)}</div>
          //     <div>PriceGrowth (Cr): ${(record.priceGrowth / 10000000).toFixed(2)}</div>
          // `;
          
          //         } else {
          //             // Fallback content if no record is found
          //             tooltipContent += `<div>No data available</div>`;
          //         }
          
          //         tooltipContent += `</div>`;
          //         return tooltipContent;
          //     }
          // }

          tooltip: {
            enabled: true,
            custom: function({ series, seriesIndex, dataPointIndex, w }) {
                // Find the region name based on seriesIndex
                const regionName = regions[seriesIndex];
        
                // Find the month corresponding to dataPointIndex (e.g., dataPointIndex 0 = month 4)
             let month =  dataPointIndex + 4;
        
             if(selectedMoths &&  selectedMoths.length > 0 ){
              // Find the earliest month and year
              const ascendingOrderData = selectedMoths.sort((a:any, b:any) => {
               return a.id - b.id;
             });
             
             if(ascendingOrderData){
               const monthsData= [
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
        
            const getFiltedData = monthsData.filter(
              month => 
                month.id >= ascendingOrderData[0].id && 
                month.id <= ascendingOrderData[ascendingOrderData.length - 1].id
            );
          
              month = getFiltedData[dataPointIndex].id;
             }
              
             }
        
                // Filter the response array to find the matching record
                const record = response.body.find(
                    (item:any) => item.region === regionName && item.month === month
                );
        
        
                // Build the tooltip content
                let tooltipContent = `
                    <div style="padding: 8px; font-size: 12px; background: white; border: 1px solid #ddd;">
                        <strong>${regionName} Region</strong>
                `;
        
                if (record) {
                    // Add data if record is found
                    // tooltipContent += `
                    //     <div>Retailers: ${record.totalRetailerCode}</div>
                    //     <div>Qty (K): ${record.totalQTY/1000}</div>
                    //     <div>Value (Cr): ${record.totalRevenue/10000000}</div>
                    // `;
                    tooltipContent += `
                      <div>RetailerGrowth: ${record.retailerGrowth}</div>
              <div>OrderGrowth (K): ${(record.orderGrowth / 1000).toFixed(2)}</div>
              <div>PriceGrowth (Cr): ${(record.priceGrowth / 10000000).toFixed(2)}</div>`;
        
                } else {
                    // Fallback content if no record is found
                    tooltipContent += `<div>No data available</div>`;
                }
        
                tooltipContent += `</div>`;
                return tooltipContent;
            }
        }
            // tooltip: {
            //   shared: true,
            //   intersect: false,
            //   custom: ({ series, dataPointIndex, w }: any) => {
            //     // Retrieve the category (month or x-axis label)
            //     const category = w.globals.labels[dataPointIndex];
            
            //     // Define column headers
            //     const headers = ['Region', 'Retailer Growth', 'Order Growth', 'Price Growth'];
            
            //     // Construct rows for each region
            //     const tableRows = series[0].map((_: any, seriesIndex: number) => {
            //       const regionName = w.globals.seriesNames[seriesIndex];
            //       const retailerGrowth = series[0][dataPointIndex].toFixed(2); // Retailer Growth (series 0)
            //       const orderGrowth = (series[1][dataPointIndex] / 1000).toFixed(2); // Order Growth (series 1, divided by 1000)
            //       const priceGrowth = (series[2][dataPointIndex] * 1000).toFixed(2); // Price Growth (series 2, multiplied by 1000)
            
            //       return `
            //         <tr>
            //           <td style="padding: 5px; border: 1px solid #ccc;">${regionName}</td>
            //           <td style="padding: 5px; border: 1px solid #ccc;">${retailerGrowth}</td>
            //           <td style="padding: 5px; border: 1px solid #ccc;">${orderGrowth}</td>
            //           <td style="padding: 5px; border: 1px solid #ccc;">${priceGrowth}</td>
            //         </tr>
            //       `;
            //     }).join('');
            
            //     // Build the final tooltip content
            //     return `
            //       <div style="padding: 10px; border: 1px solid #ccc; background-color: #fff;">
            //         <strong>${category}</strong>
            //         <table style="width: 100%; border-collapse: collapse; margin-top: 10px;">
            //           <thead>
            //             <tr style="background-color: #f2f2f2;">
            //               ${headers.map(header => `<th style="padding: 5px; border: 1px solid #ccc; text-align: left;">${header}</th>`).join('')}
            //             </tr>
            //           </thead>
            //           <tbody>
            //             ${tableRows}
            //           </tbody>
            //         </table>
            //       </div>
            //     `;
            //   }
            // }
            
            
          };
        }
      },
      (error) => {
        console.error('Data loading error:', error);
      }
    );
  };

  // This is to get the master data based in the selection.
  getMasterDataForFilter(data: any) {
    let MonthlyToalOrdaringPayload: any = {};
    let abmNameList: any = '';
    let brandList: any = '';
    let rsNameList: any = '';
    let regionList: any = '';
    let retailerTypeList: any = '';
    let payloadForMaster: any;
    if (data === 'search') {
      // Prepare comma-separated strings for each array
      abmNameList = this.selectedAbmNames.map((item) => item.id).join(', ');
      brandList = this.selectedBrands.map((item) => item.name).join(', ');
      rsNameList = this.selectedRSNames.map((item) => item.name).join(', ');
      regionList = this.selectedRegions.map((item) => item.name).join(', ');
      retailerTypeList = this.selectedRetailerTypes.map((item) => item.name).join(', ');
      const selectedCities = this.selectedCities.map((item) => item).join(', ');
      const selectedStates = this.selectedStates.map((item) => item).join(', ');
  
      MonthlyToalOrdaringPayload = {
        regionList: regionList, /// default all regions
        startDate: 20240401, /// default case start date of financial year in integer format
        endDate: 202401030, ////  default case end date of financial year in integer format
        brandList: brandList, //// default casen ""
        rsNameList: rsNameList, //// default casen ""
        abmName: abmNameList,
        retailerType: retailerTypeList,
        selectedCity:selectedCities,
        selectedState:selectedStates
      };
      payloadForMaster = {
        regionList: regionList,
        abmName: abmNameList,
        selectedCity:selectedCities,
        selectedState:selectedStates
      };
    } else {
      payloadForMaster = {
        regionList: '',
        abmName: '',
        selectedCity:'',
        selectedState:''
      };
    }
    this.GetMasterData(payloadForMaster, MonthlyToalOrdaringPayload, true);
  }

  // this is to get the monthly avg qty,value and orders.
  public chartOptionslineForOrdBhFn = (MonthlyToalOrdaringPayload?: any) => {
    this.spinner.show();
    // Define month names for easy mapping
    const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    // Prepare data for each series
    const retailersData: any = [];
    const quantityData: any = [];
    const valueData: any = [];
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
        height: 700,
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
          show: false
        },
        categories: categories,
        title: {
          text: 'Monthly Orders Trend Loading...', // Left y-axis title
          style: {
            // color: '#000000',
            fontFamily:'unset' // Change color as needed
          }
        }
      },
      yaxis: [
        {
          title: {
            text: 'Quantity (K) Loading...', // Left y-axis title
            style: { 
              fontFamily:'unset'
           }
          },
          min: 0, // Minimum value for left y-axis
          labels: {
            show: false,
            formatter: function (val) {
              // return '' + val; // Format for value
              return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
            }
          },
          tickAmount: 4 // Adjust the number of ticks as necessary
        }
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
                return val; // Format for value
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
            retailersData.push(item.noOforders / 100);
            quantityData.push(item.avgQtyPerOrder);
            valueData.push(item.avgValuePerOrder / 1000);

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
              }
            },
            dataLabels: {
              // enabled: true,
              enabled: this.isMobileView ? false : true,

              offsetX: -5,
              offsetY: -5,
              style: {
                fontSize: '10px',
                fontWeight: 'normal'
              },
              background: {
                enabled: true,
                foreColor: '#000000'
              },
              formatter: function (val: any, { seriesIndex, w,dataPointIndex }) {
                const seriesName = w.config.series[seriesIndex].name;
                if (val === 'Avg Value Per Order (K)') {
                  val = val * 1000;
                } else if (seriesName === 'No Of Orders') {
                  return (val * 100).toFixed(0); // Scale No Of Orders by 100
                }
                if (val === 'Avg Qty Per Order') {
                  val = val.toFixed(0);
                }
                // Offset Y based on series index
                // const dataLabelOffsetsY = [0, 0, 35]; // Customize offsets per series index
                // const offsetY = dataLabelOffsetsY[seriesIndex] || 0; // Use the default offset if none provided



                
  // Default offsets per series index
  const dataLabelOffsetsY = [0, 0, 35]; 
  let offsetY;

  // Adjust offset dynamically based on conditions
  if (seriesIndex === 2 && dataPointIndex === w.config.series[seriesIndex].data.length - 1) {
    offsetY = -35; // Last data point for series index 1
  } else {
    offsetY = dataLabelOffsetsY[seriesIndex] || 0; // Default offsets
  }

  // else if (seriesIndex === 1 && dataPointIndex === 0) {
  //   offsetY = 50; // First data point for series index 2
  // } 
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
                  // color: '#000000',
                  fontFamily:'unset'
                }
              },
              min: 0,
              labels: {
                formatter: function (val) {
                  return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
                },
                offsetX: -20
              },
              tickAmount: 5
            },
            tooltip: {
              shared: true,
              intersect: false,
              y: {
                formatter: function (value: any, { series, seriesIndex }) {
                  const seriesName = series[seriesIndex].name;
                  if (value === 'Avg Value Per Order (k)') {
                    value = value * 1000;
                  } else if (seriesIndex === 0) {
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
        { name: 'No Of Orders - West', group: 'retailers', data: [] }

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
        align: 'center'
        // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
      },
      yaxis: [
        {
          title: {
            text: '(No Of Orders) Loading...',
            style: { 
              fontFamily:'unset'
           }
          },
          labels: {
            show: false,
            formatter: (val: any) => '' + val
          },
          tickAmount: 4,
          max:100
        }
      ],
      xaxis: {
        labels: { trim: false, show: true },
        categories: [],
        title: { style: { color: '#000000' } }
      },
      dataLabels: {


        formatter: function (val: any, { seriesIndex, w }) {
          // Apply custom scaling to data labels based on series name
          val = val +'%'
          return val;
        },
        //  enabled: false
        enabled: this.isMobileView ? false : true,
        style: {
          fontWeight: 'normal'
        }
      },
      chart: {
        type: 'bar',
        height: 500,
        stacked: true,
      },
      plotOptions: { bar: { horizontal: false ,
        columnWidth: '50%',
      } },
      fill: { opacity: 1 },
      // colors: [
      //   '#80c7fd',
      //   '#008FFB',
      //   '#80f1cb',
      //   '#00E396',
      //   '#feb019', // Retailers colors
      //   '#FF5733',
      //   '#FFBD33',
      //   '#C70039',
      //   '#900C3F',
      //   '#581845', // Qty colors
      //   '#2ECC71',
      //   '#28B463',
      //   '#239B56',
      //   '#1D8348',
      //   '#186A3B' // Value colors
      // ],
      colors: [
        '#007bff',
        '#28a745',
        '#ffc107', 
        '#dc3545', 
        '#ff5733',  // Blue, red-orange, green, amber, crimson
        // '#6f42c1',
        // '#17a2b8',
        // '#fd7e14',
        // '#6610f2',
        // '#e83e8c', // Purple, teal, orange, violet, pink
        // '#20c997',
        // '#ff6347',
        // '#8b0000',
        // '#4169e1',
        // '#32cd32' // Aqua, tomato, dark red, royal blue, lime green
      ],
      legend: { position: 'bottom', horizontalAlign: 'center' }
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
            const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
            const metricLabels: { [key: string]: string } = {
              totalRetailerCode: 'No Of Orders %',
              totalQTY: 'Total Qty (K)',
              totalRevenue: 'Total Value (Cr)'
            };

            return regions.map((region) => {
              const regionData = data.filter((item) => item.region === region);
              return {
                name: `${metricLabels[metric]} - ${region}`,
                group: group,
                data: regionData.map((item) => {
                  if (metric === 'totalQTY') return (item.totalQTY / 10).toFixed(0); // Adjust units
                  if (metric === 'totalRevenue') return (item.totalRevenue / 10000000).toFixed(2); // Adjust units
                  return item.noOfOrdersPercentage.toFixed(2);
                })
              };
            });
          };

          // Update chart options with dynamic series and categories
          this.RegionWiseMonthlyDistibutionOptionsFOrdBh = {
            ...this.RegionWiseMonthlyDistibutionOptionsFOrdBh,
            series: [
              ...prepareSeriesData(response.body, 'totalRetailerCode', 'No Of Orders')
              // ...prepareSeriesData(response.body, 'totalQTY', 'qty'),
              // ...prepareSeriesData(response.body, 'totalRevenue', 'value')
            ],
            xaxis: { ...this.RegionWiseMonthlyDistibutionOptionsFOrdBh.xaxis, categories },
            title: {
              text: 'Region Wise Monthly Distribution - No Of Orders',
              align: 'center'
              // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
            },
            yaxis: [
              {
                title: {
                  text: '( No Of Orders % )',
                  style: { fontFamily:'unset'},
                  offsetX:15
                },
                labels: {
                  formatter: (val: any) => '' + val
                },
                tickAmount: 4,
                max:100,
                min:0
              }
            ]
          };
        }
      },
      (error) => {
        this.spinner.hide();
        console.error('Error fetching region-wise monthly distribution data:', error);
      }
    );
  };

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
      yaxis: [
        {
          title: {
            text: '(Avg Qty/Value Per Order)',
            style: { color: '#000000',
              fontFamily:'unset'
             }
          },
          labels: {
            show: false,
            formatter: (val: any) => '' + val
          },
          tickAmount: 4
        }
      ],
      xaxis: {
        labels: { trim: false, show: true },
        categories: [],
        title: { style: { color: '#000000' } }
      },
      dataLabels: {
        // enabled: false
        enabled: this.isMobileView ? false : true,
        style: {
          fontWeight: 'normal'
        }
      },
      chart: { type: 'bar', height: 700, stacked: true },
      plotOptions: { bar: { horizontal: false } },
      fill: { opacity: 1 },
      colors: [
        // '#80c7fd',
        // '#008FFB',
        // '#80f1cb',
        // '#00E396',
        // '#feb019', // Qty colors
        // '#FF5733',
        // '#FFBD33',
        // '#C70039',
        // '#900C3F',
        // '#581845' // Value colors
       '#007bff',
        '#28a745',
        '#ffc107', 
        '#dc3545', 
        '#ff5733', 
      ],
    legend: {
      position: 'bottom',
      horizontalAlign: 'left',
      fontSize: '12px',
      itemMargin: {
        horizontal: 5,
        vertical: 5,
      },
      formatter: (seriesName, opts) => seriesName,
      onItemClick: {
        toggleDataSeries: true,
      },
    },
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
            const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
            const metricLabels: { [key: string]: string } = {
              avgQtyPerOrder: 'Avg Qty Per Order',
              avgPricePerOrder: 'Avg Value Per Order (K)'
            };

            return regions.map((region) => {
              const regionData = data.filter((item) => item.region.toUpperCase() === region.toUpperCase());
              return {
                name: `${metricLabels[metric]} - ${region}`,
                group: group,
                data: regionData.map((item) => {
                  if (metric === 'avgQtyPerOrder') return item.avgQtyPerOrder.toFixed(2); // Adjust units
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
            yaxis: [
              {
                title: {
                  text: '(Avg Qty/Value Per Order)',
                  style: { 
                    fontFamily:'unset'
                 }
                },
                labels: { formatter: (val: any) => '' + val },
                tickAmount: 4
              }
            ]
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

   prepareOrderMessage(selectedYears:any, selectedMonths:any) {
    if (selectedYears.length === 0 && selectedMonths.length === 0) {
        return "No data available.";
    }

    // Find the earliest month and year
    const sortedMonths = selectedMonths.sort((a:any, b:any) => {
        return a.id - b.id;
    });

    const startYear =  Math.min(...selectedYears);
    const endMonth = sortedMonths.length > 0 ? sortedMonths[sortedMonths.length - 1].name : "January";

    const EndYear =  Math.max(...selectedYears);
    const startMonth = sortedMonths.length > 0 ? sortedMonths[0].name : "January";


    // Get current date
    const currentDate = new Date();
    const currentMonth = currentDate.toLocaleString('default', { month: 'long' });
    const currentYear = currentDate.getFullYear();

    const date = new Date(
      parseInt(this.getLastUpdatedRecordDate.slice(0, 4)), // Year
      parseInt(this.getLastUpdatedRecordDate.slice(4, 6)) - 1, // Month (0-indexed)
      parseInt(this.getLastUpdatedRecordDate.slice(6, 8)) // Day
    );
  
    const day = date.getDate();
    const month = date.toLocaleString('default', { month: 'long' });
    const year = date.getFullYear();
  
    // Add ordinal suffix to the day
    const ordinalSuffix = (d:any) => {
      if (d > 3 && d < 21) return 'th';
      switch (d % 10) {
        case 1: return 'st';
        case 2: return 'nd';
        case 3: return 'rd';
        default: return 'th';
      }
    };
  
   const getLastUpdatedDate =  `${day}${ordinalSuffix(day)} ${month} ${year}`;
    // if(endMonth == currentMonth){

      return `From 1st ${startMonth} ${startYear} to ${getLastUpdatedDate} order details.`;
    // }

    // return `From 1st ${startMonth} ${startYear} to ${endMonth} ${EndYear} order details.`;
}



  toggleMonthSelection(month: any) {
    if (this.selectedMonths.includes(month)) {
      this.selectedMonths = this.selectedMonths.filter((m) => m.id !== month.id);
    } else {
      this.selectedMonths.push(month);
    }
    this.areAllMonthsSelected = this.selectedMonths.length === this.availableMonths.length;
  }

  removeSelectedMonth(month: any, event: Event) {
    event.stopPropagation();
    this.selectedMonths = this.selectedMonths.filter((m) => m.id !== month.id);
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
    this.filteredMonthsList = this.availableMonths.filter((month) => month.name.toLowerCase().includes(searchValue));
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
    this.growthOverPreviousMonthYear = {};
    this.RegionWiseGrowthOverPreviousMonthOptionsPrevYear = {};
    // Menu id 3 specific charts
    this.chartOptionslineForOrdBh = {};
    this.RegionWiseMonthlyDistibutionOptionsFOrdBh = {};
    this.RegionWiseMonthlyAvgPerOrder = {};
    this.percentageOfOrdersOfDayInMonthOptions={};
    this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart = {};
    this.percentageofOrdersByWeekdayorWeekendRegionWiseOptions={};
    this.percentageofOrdersByHourOfTheDayOptionsBarchart={};
    this.overallOrders = [];
    this.regionTotals = [];
    this.rsTotals = [];
    this.displayShowMore = true;
    this.displayShowMoreForRegionOrderLevel = true;
    this.displayShowMoreForRsOrderLevel = true;
    this.selectedSelectionTypeForUI ='Order Wise';
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
        zoom: {
          enabled: false
        }
      },
      dataLabels: {
        style: {
          fontWeight: 'noraml'
        },
        enabled: true, // Enable data labels
        formatter: (val: any) => `${val.toFixed(0)}%` // Format as percentage
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
        }
      },
      yaxis: {
        title: {
          text: 'Order Percentage',
          style: {
            // color: '#000000',
            fontFamily:'unset'
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
    const pieSeriesData: number[] = []; // For percentage data for Weekday and Weekend
    const pieLabels: string[] = []; // For labeling the pie chart slices
    const barCategories: string[] = ['Weekday', 'Weekend'];
    const barSeriesData: number[] = []; // For order count for Weekday and Weekend
    const distinctOrderCounts: number[] = []; // To store distinct order counts for tooltip

    // Define Pie Chart options
    this.percentageofOrdersbyWeekdayorWeekendOptionsPiechart = {
      series: [],
      chart: {
        type: 'pie',
        height: 500
      },
      dataLabels: {
        style: {
          fontWeight: 'noraml'
        }
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
      },
      yaxis: {
        labels: {
          formatter: (val: any) => `${val.toFixed(0)}%`
        }
      }
    };

    
    this.percentageofOrdersbyWeekdayorWeekendOptionsBarchart = {
      series: [
        {
          name: 'Percentage of Orders',
          data: barSeriesData // Show percentage here
        }
      ],
      chart: {
        height: 500,
        type: 'bar'
      },
      plotOptions: {
        bar: {
          columnWidth: '45%',
          distributed: true // Enable distributed colors for each bar
        }
      },

      dataLabels: {
        enabled: true,
        style: {
          fontWeight: 'normal'
        },
        formatter: (val: any) => `${val.toFixed(2)}%`
      },
      title: {
        text: 'Order Count by Weekday/Weekend Loading...',
        align: 'center'
      },
      xaxis: {
        categories: barCategories,
        title: {
          text: 'Day Type'
        },
        labels: {
          style: {
            fontSize: '12px'
          }
        }
      },
      yaxis: {
        title: {
          text: 'Percentage of Orders',
          style:{
             fontFamily:'unset'
          }
        },
        labels: {
          formatter: (val: any) => `${val.toFixed(0)}%`
        }
      },
      colors: [
        '#007bff',
        'rgb(0, 227, 150)',
        '#80f1cb',
        '#00E396',
        '#feb019',
        '#FF5733',
        '#FFBD33',
        '#C70039',
        '#900C3F',
        '#581845',
        '#2ECC71',
        '#28B463',
        '#239B56',
        '#1D8348',
        '#186A3B',
        '#FF33FF',
        '#FF5733',
        '#FFC300',
        '#DAF7A6',
        '#581845',
        '#900C3F',
        '#C70039',
        '#FF5733',
        '#FFC300',
        '#FF33FF',
        '#4B0082',
        '#7B68EE',
        '#6A5ACD',
        '#8A2BE2',
        '#9932CC',
        '#FF6347',
        '#4682B4',
        '#40E0D0',
        '#00CED1',
        '#20B2AA',
        '#FF1493',
        '#FF4500',
        '#DA70D6',
        '#FF69B4',
        '#DB7093',
        '#CD5C5C',
        '#8B0000',
        '#556B2F',
        '#2F4F4F',
        '#D2691E',
        '#6495ED',
        '#FF7F50',
        '#7FFF00',
        '#7CFC00',
        '#ADFF2F',
        '#32CD32',
        '#66CDAA',
        '#8FBC8F',
        '#3CB371',
        '#228B22'
      ], // Unique colors for each bar
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
        show: false
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
    const barCategories: string[] = []; // For x-axis categories (regions)
    const weekdayData: number[] = []; // For Weekday data
    const weekendData: number[] = []; // For Weekend data
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
        text: 'Order Count by Region (Weekday vs Weekend) Loading...',
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
          text: 'Percentage of Orders',
          style:{
            fontFamily:'unset'
         },
        },
        labels: {
          formatter: (val: any) => `${val.toFixed(0)}%`
        }
      },
      tooltip: {
        shared: true,
        intersect: false,
        custom: ({ series, seriesIndex, dataPointIndex, w }) => {
          const distinctOrderCount =
            seriesIndex === 0
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
          const groupedData = response.body.reduce((acc: any, item: any) => {
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
          fontSize: '10px'
        },
        background: {
          enabled: true,
          foreColor: '#000000'
        }
      },
      title: {
        text: 'Order Percentage by Hour (Weekday vs Weekend) Loading...',
        align: 'center'
      },
      xaxis: {
        categories: hours,
        title: {
          text: 'Hour of the Day'
        }
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '80%'
          // endingShape: "rounded"
        }
      },
      yaxis: {
        title: {
          text: 'Percentage of Orders',
          style:{
            fontFamily:'unset'
         },
        },
        labels: {
          formatter: (val: any) => `${val.toFixed(0)}%`
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
              text: weekendDataAvailable ? 'Order Percentage by Hour (Weekday vs Weekend)' : 'Order Percentage by Hour (Weekday)',
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

  // Compute the total order quantity
  get totalOrderQty(): number {
    return this.orders.reduce((acc, order) => acc + order.orderQty, 0);
  }

  // Helper method to aggregate data by a given field (region or rs)
  private aggregateData(field: string): [string, number][] {
    const totals: { [key: string]: number } = this.orders.reduce((acc: any, order: any) => {
      acc[order[field]] = (acc[order[field]] || 0) + order.orderQty;
      return acc;
    }, {});

    return Object.entries(totals) as [string, number][]; // Cast to the correct type
  }

  // this is for getting the top sku qty.

  public topSKUOrderedOverall = (MonthlyTotalOrderingPayload?: any) => {
    this.spinner.show();
    this.overallOrders = [];
    this.dashboardService.topSKUOrderedOverall(MonthlyTotalOrderingPayload).subscribe(
      async (response) => {
        if (response && response.body) {
          // this.overallOrders = response.body.map((order:any) => {
          //   // Process productCode to remove slash if present
          //   let result = order.productCode.includes("/") ? order.productCode.replace("/", "") : order.productCode;
          //   // Create the image source URL based on the processed productCode
          //   const selectedImage = `assets/skuimages/${result}.jpg`;
          //   // Return the modified order with the new imageSrc field
          //   return {
          //     ...order,
          //     imageSrc: selectedImage
          //   };
          // });


         const overallOrders = await Promise.all(response.body.map(async (order: any) => {
            // Process productCode to remove slash if present
            let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
          
            // Create the image source URL based on the processed productCode
            const selectedImage = `assets/skuimages/${result}.jpg`;
          
            // Check if the image exists
            const imageExists = await this.dashboardService.checkImageExists(selectedImage);
          
            // Return the modified order with the new imageSrc and errorImg field
            return {
              ...order,
              imageSrc: selectedImage,
              errorImg: !imageExists // If image doesn't exist, set errorImg to true
            };
           
          }));

          this.overAllResponseOrderLevel = overallOrders;
          this.overallOrders = overallOrders.slice(0, 5);
          this.selectImage(this.overallOrders[0],0);
          this.calculateOverallGrandTotal();
          this.topSKUOrderedRegionSelected(MonthlyTotalOrderingPayload);
        }
      },
      (error) => {
        console.error('Error fetching monthly order data:', error);
        this.spinner.hide();
      }
    );
  };

  // this is for getting the top sku qty.

  public topSKUOrderedRegionSelected = (MonthlyTotalOrderingPayload?: any) => {
    this.spinner.show();
    this.regionTotals = [];
    this.dashboardService.topSKUOrderedRegionSelected(MonthlyTotalOrderingPayload).subscribe(
      async (response) => {
        if (response && response.body) {
          this.regionTotals = response.body;

          this.regionTotals = await Promise.all(response.body.map(async (order: any) => {
            // Process productCode to remove slash if present
            let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
          
            // Create the image source URL based on the processed productCode
            const selectedImage = `assets/skuimages/${result}.jpg`;
          
            // Check if the image exists
            const imageExists = await this.dashboardService.checkImageExists(selectedImage);
          
            // Return the modified order with the new imageSrc and errorImg field
            return {
              ...order,
              imageSrc: selectedImage,
              errorImg: !imageExists // If image doesn't exist, set errorImg to true
            };
          }));
          this.overAllResponseRegionLevel = this.regionTotals;
          this.regionTotals = this.regionTotals.slice(0,5);

          if(this.regionTotals && this.regionTotals.length>0){
            this.selectImageforregion(this.regionTotals[0],0);
          }
          this.getRegionGrandTotal();
          this.topSKUOrderedRSNameSelected(MonthlyTotalOrderingPayload);
        }
      },
      (error) => {
        console.error('Error fetching monthly order data:', error);
        this.spinner.hide();
      }
    );
  };

  // Method to calculate grand total
  calculateOverallGrandTotal() {
    this.overallGrandTotal = this.overallOrders.reduce((acc: any, order: any) => acc + order.totalOrderQty, 0);
  }

  // Method to calculate grand total
  calculateOverallGrandTotalForTotalPrice() {
    this.overallGrandTotal = this.overallOrders.reduce((acc: any, order: any) => acc + order.totalOrderQty, 0);
  }

  

  // Method to calculate grand total
  getRegionGrandTotal() {
    this.overallRegionGrandTotal = this.regionTotals.reduce((acc: any, order: any) => acc + order.totalOrderQty, 0);
  }

  // Method to calculate grand total
  getRegionGrandTotalForTotalPrice() {
    this.overallRegionGrandTotal = this.regionTotals.reduce((acc: any, order: any) => acc + order.totalOrderQty, 0);
  }

  // Method to calculate grand total
  getRSGrandTotal() {
    this.overallRsGrandTotal = this.rsTotals.reduce((acc: any, order: any) => acc + order.totalOrderQty, 0);
  }

  // Method to calculate grand total
  getRSGrandTotalFortotalPrice() {
    this.overallRsGrandTotal = this.rsTotals.reduce((acc: any, order: any) => acc + order.totalOrderQty, 0);
  }

  // This is to get the rs names data.
  public topSKUOrderedRSNameSelected = (MonthlyTotalOrderingPayload?: any) => {
    this.spinner.show();
    this.rsTotals = [];
    this.dashboardService.topSKUOrderedRSNameSelected(MonthlyTotalOrderingPayload).subscribe(
      async (response) => {
        if (response && response.body) {

          
          this.rsTotals = response.body;
          this.rsTotals = await Promise.all(response.body.map(async (order: any) => {
            // Process productCode to remove slash if present
            let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
          
            // Create the image source URL based on the processed productCode
            const selectedImage = `assets/skuimages/${result}.jpg`;
          
            // Check if the image exists
            const imageExists = await this.dashboardService.checkImageExists(selectedImage);
          
            // Return the modified order with the new imageSrc and errorImg field
            return {
              ...order,
              imageSrc: selectedImage,
              errorImg: !imageExists // If image doesn't exist, set errorImg to true
            };
          }));
this.overAllResponseRsOrderLevel = this.rsTotals;
this.rsTotals = this.rsTotals.slice(0,5);

          if(this.rsTotals && this.rsTotals.length>0){
            this.selectedImageSourceForRsFn(this.rsTotals[0],0);
          }
          this.getRSGrandTotal();
        }
      },
      (error) => {
        console.error('Error fetching monthly order data:', error);
        this.spinner.hide();
      }
    );
  };

  getCurrentMonthToDate() {
    const currentDate = new Date();
    const currentMonth = this.datePipe.transform(currentDate, 'MMMM'); // Get current month name (e.g., 'November')
    const currentYear = this.datePipe.transform(currentDate, 'yyyy'); // Get current year (e.g., '2024')
    const currentDay = currentDate.getDate(); // Get current day (e.g., '29')

    // this.currentMonthToDateText = `From ${currentMonth} ${currentYear} to till date order details.`;
  }




  // This is to get the growth over previous year month.
  public GrowthOverPreviousYear = (MonthlyToalOrdaringPayload?: any) => {
    this.spinner.show();

    // Define month names for easy mapping
    const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    // Prepare data for each series
    const retailersData: any = [];
    const quantityData: any = [];
    const valueData: any = [];
    const categories: string[] = []; // This will hold the month names
    this.growthOverPreviousMonthYear = {
      chart: {
        type: 'bar',
        // height: 500,
        height: 1000,
        toolbar: {
          show: true,
          tools: {
            zoom: true, // Enable zoom
            zoomin: true, // Enable zoom-in
            zoomout: true, // Enable zoom-out
            pan: true, // Enable panning
            reset: true // Reset zoom and pan to the initial state
          },
          autoSelected: 'zoom' // Set default tool to zoom
        }
      },
      title: {
        text: 'Growth Over Previous Year Loading...', // Chart title
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
      // dataLabels: {
      //   enabled: this.isMobileView ? false: true,
      //   background: {
      //     enabled: true,
      //     foreColor: '#000000'
      //   },
      // },
      dataLabels: {
        enabled: this.isMobileView ? false : true,
        style: {
          colors: ['#000000'] // Set the color of the data labels to black
        },
        background: {
          enabled: true,
          foreColor: '#000000' // This controls the color of the text in the background
        }
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
        }
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
              // color: '#000000',
              fontFamily:'unset' // Change color as needed
            }
          },
          // min: 0, // Minimum value for left y-axis
          labels: {
            show: false,
            formatter: function (val: any) {
              return '' + val; // Format for value
            }
          },
          tickAmount: 1 // Adjust the number of ticks as necessary
        }
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
    this.dashboardService.GrowthOverPreviousYear(MonthlyToalOrdaringPayload).subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();
          this.RegionWiseMonthlyDistribution(MonthlyToalOrdaringPayload);

          response.body.forEach((item: any) => {
            // Push values to each series array
            // retailersData.push(item.retailerGrowth);
            // quantityData.push(item.orderGrowth);
            // valueData.push(item.priceGrowth);
            // retailersData.push(item.retailerGrowth / 6);
            //  retailersData.push(item.retailerGrowthPercentage);
            // quantityData.push((item.orderGrowth / 1000).toFixed(2)); // Converts orderGrowth to thousands
            // valueData.push((item.priceGrowth / 10000000).toFixed(2)); // Converts priceGrowth to crores


            retailersData.push(item.retailerGrowth / 6);
            quantityData.push((item.orderGrowth / 1000).toFixed(2)); // Converts orderGrowth to thousands
            valueData.push((item.priceGrowth / 10000000).toFixed(2)); // Converts priceGrowth to crores

            // Get month name and add to categories
            categories.push(monthNames[item.month - 1]);
          });

          this.growthOverPreviousMonthYear = {
            chart: {
              zoom: {
                enabled: true
              },
              type: 'bar',
              height: 1000,
              toolbar: {
                show: true,
                tools: {
                  zoom: true, // Enable zoom
                  zoomin: true, // Enable zoom-in
                  zoomout: true, // Enable zoom-out
                  pan: true, // Enable panning
                  reset: true // Reset zoom and pan to the initial state
                }
                // autoSelected: 'zoom'    // Set default tool to zoom
              }
            },
            title: {
              text: 'Growth Over Previous Year', // Chart title
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
              }
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
            // dataLabels: {
            //   // enabled: true,
            //   enabled: this.isMobileView ? false: true,

            //   //  offsetY: -10,

            //   style: {
            //     fontSize: "10px",
            //     fontWeight: 'normal' // Remove bold styling by setting fontWeight to normal

            //   },

            // },

            dataLabels: {
              enabled: this.isMobileView ? false : true,
              style: {
                colors: ['#000000'],
                fontWeight: 'normal' // Set the color of the data labels to black
              },
              formatter: function (val: any, { seriesIndex }) {
                // Check if the current series is for retailer values (this can be based on seriesIndex or other criteria)
                if (seriesIndex === 0) {
                  val = val * 6;
                  // return   val = (val).toFixed(2) + ' %'; // Multiply by 6 and format to 2 decimal places
                }

                // Return the formatted value, rounding as needed
                return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
              }
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
                  text: ' (Growth Value)' ,// Left y-axis title
                  // style: {
                  //   color: '#000000' // Change color as needed
                  // }
                  style: {
                    fontFamily: 'unset'
                  },
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
              //   y: {
              //     formatter: function (val) {
              //       return val % 1 === 0 ? val.toFixed(0) : val.toFixed(2);
              //     }
              //   }
              // }
              y: {
                formatter: function (val:any, { seriesIndex }) {
                  // Check if the current series is for retailer values (this can be based on seriesIndex or other criteria)
                  if (seriesIndex === 0) {
                    val = val * 6;
                    // return   val = (val).toFixed(2) + ' %'; // Multiply by 6 and format to 2 decimal places
                  }

                  // Return the formatted value, rounding as needed
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



  // This is to get the all year data comaprision for evry month.
  public RegionWiseGrowthOverPreviousMonthForPreviousYear = (MonthlyToalOrdaringPayload?: any) => {
    this.spinner.show();

    const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    const categories: string[] = [];
    const isMobile = window.innerWidth <= 768;

    this.RegionWiseGrowthOverPreviousMonthOptionsForPrevYear = {
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
        text: 'Region Wise  Year Growth Loading...',
        align: 'center'
        // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
      },
      yaxis: [
        {
          title: {
            text: '(Growth Value)',
            style: { 

                fontFamily:'unset'
             }
          },
          labels: {
            show: false,
            formatter: (val: any) => '' + val
          },
          tickAmount: 4
        }
      ],
      xaxis: {
        labels: { trim: false, show: true },
        categories: [],
        title: { style: { color: '#000000' } }
      },
      dataLabels: {
        //  enabled: true,
        enabled: this.isMobileView ? false : true,

        style: {
          fontSize: '10px',
          colors: ['#000000'],
          fontWeight: 'normal' // Remove bold styling by setting fontWeight to normal
        }
      },
      chart: {
        type: 'bar',
        height: 500,
        stacked: true
      },
      plotOptions: { bar: { horizontal: false } },
      fill: { opacity: 1 },
      // colors: [
      //     '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019',
      //     '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845',
      //     '#2ECC71', '#28B463', '#239B56', '#1D8348', '#186A3B'
      // ],t
      colors: [
        '#007bff',
        '#ff5733',
        '#28a745',
        '#ffc107',  
        '#dc3545', // Blue, red-orange, green, amber, crimson
        // '#6f42c1',
        // '#17a2b8',
        // '#fd7e14',
        // '#6610f2',
        // '#e83e8c', // Purple, teal, orange, violet, pink
        // '#20c997',
        // '#ff6347',
        // '#8b0000',
        // '#4169e1',
        // '#32cd32' // Aqua, tomato, dark red, royal blue, lime green
      ],
      legend: { position: isMobile ? 'top' : 'right', horizontalAlign: 'left' }
    };

    this.dashboardService.RegionWiseGrowthOverPreviousMonthForPreviousYear(MonthlyToalOrdaringPayload).subscribe(
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
            const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
            const metricLabels: { [key: string]: string } = {
              retailerGrowth: 'Retailer Growth',
              orderGrowth: 'Order Growth (K)',
              priceGrowth: 'Price Growth (Cr)'
            };

            return regions.map((region) => {
              const regionData = data.filter((item) => item.region === region);
              return {
                name: `${metricLabels[metric]} - ${region}`,
                group: group,
                data: regionData.map((item) => {
                  if (metric === 'orderGrowth') return (item.orderGrowth / 1000).toFixed(2); // Adjust units
                  if (metric === 'priceGrowth') return ((item.priceGrowth / 10000000).toFixed(2)); // Adjust units
                  return item.retailerGrowth;
                })
              };
            });
          };


          const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];

          // Update chart options with dynamic series and categories
          this.RegionWiseGrowthOverPreviousMonthOptionsForPrevYear = {
            ...this.RegionWiseGrowthOverPreviousMonthOptionsForPrevYear,
            series: [
              // ...prepareSeriesData(response.body, 'retailerGrowth', 'retailers'),
              // ...prepareSeriesData(response.body, 'orderGrowth', 'qty'),
              ...prepareSeriesData(response.body, 'priceGrowth', 'value')
            ],
            xaxis: { ...this.RegionWiseGrowthOverPreviousMonthOptionsForPrevYear.xaxis, categories },
            title: {
              text: 'Region Wise Year Growth',
              align: 'center'
              // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
            },
            yaxis: [
              {
                title: {
                  text: '(Quantity)',
                  style: { color: '#000000' }
                },
                labels: {
                  formatter: (val: any) => '' + val
                },
                tickAmount: 4
              }
            ],

            tooltip: {
              enabled: true,
              custom: function({ series, seriesIndex, dataPointIndex, w }) {
                  // Find the region name based on seriesIndex
                  const regionName = regions[seriesIndex];
          
                  // Find the month corresponding to dataPointIndex (e.g., dataPointIndex 0 = month 4)
                  const month = dataPointIndex + 4;
          
                  // Filter the response array to find the matching record
                  const record = response.body.find(
                      (item:any) => item.region === regionName && item.month === month
                  );
          
                  // Build the tooltip content
                  let tooltipContent = `
                      <div style="padding: 8px; font-size: 12px; background: white; border: 1px solid #ddd;">
                          <strong>${regionName} Region</strong>
                  `;
          
                  if (record) {
                      // Add data if record is found
                      // tooltipContent += `
                      //     <div>Retailers: ${record.totalRetailerCode}</div>
                      //     <div>Qty (K): ${record.totalQTY/1000}</div>
                      //     <div>Value (Cr): ${record.totalRevenue/10000000}</div>
                      // `;


                      
                      tooltipContent += `
              <div>RetailerGrowth: ${record.retailerGrowth}</div>
              <div>OrderGrowth (K): ${(record.orderGrowth / 1000).toFixed(2)}</div>
              <div>PriceGrowth (Cr): ${(record.priceGrowth / 10000000).toFixed(2)}</div>
          `;
          
                  } else {
                      // Fallback content if no record is found
                      tooltipContent += `<div>No data available</div>`;
                  }
          
                  tooltipContent += `</div>`;
                  return tooltipContent;
              }
          }
            // tooltip: {
            //   shared: true,
            //   intersect: false,
            //   custom: ({ series, dataPointIndex, w }: any) => {
            //     // Retrieve the category (month or x-axis label)
            //     const category = w.globals.labels[dataPointIndex];
            
            //     // Define column headers
            //     const headers = ['Region', 'Retailer Growth', 'Order Growth', 'Price Growth'];
            
            //     // Construct rows for each region
            //     const tableRows = series[0].map((_: any, seriesIndex: number) => {
            //       const regionName = w.globals.seriesNames[seriesIndex];
            //       const retailerGrowth = series[0][dataPointIndex].toFixed(2); // Retailer Growth (series 0)
            //       const orderGrowth = (series[1][dataPointIndex] / 1000).toFixed(2); // Order Growth (series 1, divided by 1000)
            //       const priceGrowth = (series[2][dataPointIndex] * 1000).toFixed(2); // Price Growth (series 2, multiplied by 1000)
            
            //       return `
            //         <tr>
            //           <td style="padding: 5px; border: 1px solid #ccc;">${regionName}</td>
            //           <td style="padding: 5px; border: 1px solid #ccc;">${retailerGrowth}</td>
            //           <td style="padding: 5px; border: 1px solid #ccc;">${orderGrowth}</td>
            //           <td style="padding: 5px; border: 1px solid #ccc;">${priceGrowth}</td>
            //         </tr>
            //       `;
            //     }).join('');
            
            //     // Build the final tooltip content
            //     return `
            //       <div style="padding: 10px; border: 1px solid #ccc; background-color: #fff;">
            //         <strong>${category}</strong>
            //         <table style="width: 100%; border-collapse: collapse; margin-top: 10px;">
            //           <thead>
            //             <tr style="background-color: #f2f2f2;">
            //               ${headers.map(header => `<th style="padding: 5px; border: 1px solid #ccc; text-align: left;">${header}</th>`).join('')}
            //             </tr>
            //           </thead>
            //           <tbody>
            //             ${tableRows}
            //           </tbody>
            //         </table>
            //       </div>
            //     `;
            //   }
            // }
            
            
          };
        }
      },
      (error) => {
        console.error('Data loading error:', error);
      }
    );
  };

  selectImage(data: any,index:any) {
    this.selectedOrder = data.productCode;
    let result =  data.productCode.includes("/") ?  data.productCode.replace("/P", "") :  data.productCode;
    const selectedImage = `assets/skuimages/${result}.jpg`
    this.currentOverallLevel = index;
    this.selectedImageSource = selectedImage;
  }
  
// this is for image for regionwise
  selectImageforregion(data: any,index:any) {
    this.selectedOrderForRegion = data.productCode;
    let result =  data.productCode.includes("/") ?  data.productCode.replace("/P", "") :  data.productCode;
    const selectedImage = `assets/skuimages/${result}.jpg`
    // this.currentOverallLevel = index;
    this.selectedImageSourceForRegion = selectedImage;
  }

  // this is for image for regionwise
  selectedImageSourceForRsFn(data: any,index:any) {
    this.selectedOrderForRs = data.productCode;
    let result =  data.productCode.includes("/") ?  data.productCode.replace("/P", "") :  data.productCode;
    const selectedImage = `assets/skuimages/${result}.jpg`
    // this.currentOverallLevel = index;
    this.selectedImageSourceForRs = selectedImage;
  }

// Method to handle image load errors
onImageError(event: Event): void {
  const imgElement = event.target as HTMLImageElement;
  console.log('Image failed to load:', imgElement.src);
} 




// this is for state filter.

toggleDropdownVisibilityForState() {
  // this.isDropdownOpenForState = !this.isDropdownOpenForState;

  this.toggleDropdownVisibility1('isDropdownOpenForState');
  if (this.isDropdownOpenForState) {
    this.filteredStatesList = this.availableStates; // Reset filtered list on opening
  }
}

toggleAllStateSelection(event: Event) {
  this.areAllStatesSelected = (event.target as HTMLInputElement).checked;
  this.selectedStates = this.areAllStatesSelected
    ? [...this.filteredStatesList]
    : [];
}

toggleStateSelection(state: string) {
  if (this.selectedStates.includes(state)) {
    this.selectedStates = this.selectedStates.filter((s) => s !== state);
  } else {
    this.selectedStates.push(state);
  }
  this.getMasterDataForFilter('search');
  this.areAllStatesSelected = this.selectedStates.length === this.filteredStatesList.length;
}

removeSelectedState(state: string, event: Event) {
  event.stopPropagation();
  this.selectedStates = this.selectedStates.filter((s) => s !== state);
  this.areAllStatesSelected = false;
}

filterAvailableStates() {
  const searchValue = this.searchInputValueForState.toLowerCase();
  this.filteredStatesList = this.availableStates.filter((state) =>
    state.toLowerCase().includes(searchValue)
  );
  this.areAllStatesSelected = this.filteredStatesList.length > 0 &&
    this.selectedStates.length === this.filteredStatesList.length;
}

// this is for city filter functionality.


toggleDropdownVisibilityForCity() {
  this.toggleDropdownVisibility1('isDropdownOpenForCity');
  if (this.isDropdownOpenForCity) {
    this.filteredCitiesList = this.availableCities; // Reset filtered list on opening
  }
}

toggleAllCitySelection(event: Event) {
  this.areAllCitiesSelected = (event.target as HTMLInputElement).checked;
  this.selectedCities = this.areAllCitiesSelected
    ? [...this.filteredCitiesList]
    : [];
}

toggleCitySelection(city: string) {
  if (this.selectedCities.includes(city)) {
    this.selectedCities = this.selectedCities.filter((c) => c !== city);
  } else {
    this.selectedCities.push(city);
  }

  this.getMasterDataForFilter('search');
  this.areAllCitiesSelected =
    this.selectedCities.length === this.filteredCitiesList.length;
}

removeSelectedCity(city: string, event: Event) {
  event.stopPropagation();
  this.selectedCities = this.selectedCities.filter((c) => c !== city);
  this.areAllCitiesSelected = false;
}

filterAvailableCities() {
  const searchValue = this.searchInputValueForCity.toLowerCase();
  this.filteredCitiesList = this.availableCities.filter((city) =>
    city.toLowerCase().includes(searchValue)
  );
  this.areAllCitiesSelected =
    this.filteredCitiesList.length > 0 &&
    this.selectedCities.length === this.filteredCitiesList.length;
}

// this is to get the all orders.
  async addMoreOrders(type:any){
  if(type == 'oveall'){
    if(this.displayShowMore){
      this.overallOrders = this.overAllResponseOrderLevel;
    } else {
      this.overallOrders = this.overallOrders.slice(0, 5);
    }
    if(this.selectedSelectionTypeForUI === 'Order Wise'){
      this.calculateOverallGrandTotal();
    } else {
      this.calculateOverallGrandTotalForTotalPrice();
    }
    this.displayShowMore = !this.displayShowMore;
  } 

  if(type == 'region'){
    if(this.displayShowMoreForRegionOrderLevel){
      this.regionTotals = this.overAllResponseRegionLevel;
    } else {
      this.regionTotals = this.regionTotals.slice(0, 5);
    }
    if(this.selectedSelectionTypeForUI === 'Order Wise'){
      this.getRegionGrandTotal();
    } else {
      this.getRegionGrandTotalForTotalPrice();
    }

    this.displayShowMoreForRegionOrderLevel = !this.displayShowMoreForRegionOrderLevel;
  } 

  if(type == 'rs'){
    if(this.displayShowMoreForRsOrderLevel){
      this.rsTotals = this.overAllResponseRsOrderLevel;
    } else {
      this.rsTotals = this.rsTotals.slice(0, 5);
    }

    if(this.selectedSelectionTypeForUI === 'Order Wise'){
      this.getRSGrandTotal();
    } else {
      this.getRSGrandTotalFortotalPrice();
    }
    this.displayShowMoreForRsOrderLevel = !this.displayShowMoreForRsOrderLevel;
  } 


}

public regionwiseGrowthOverPreviousYearMonthly = (MonthlyToalOrdaringPayload?: any) => {
  this.spinner.show();

  const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  const categories: string[] = [];
  const isMobile = window.innerWidth <= 768;

  this.RegionWiseGrowthOverPreviousMonthOptionsPrevYear = {
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
      text: 'Region Wise Growth Over Previous Year Loading...',
      align: 'center'
      // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
    },
    yaxis: [
      {
        title: {
          text: '(Growth Value)',
          style: { 
            fontFamily:'unset'
         }
        },
        labels: {
          show: false,
          formatter: (val: any) => '' + val
        },
        tickAmount: 4
      }
    ],
    xaxis: {
      labels: { trim: false, show: true },
      categories: [],
      title: { style: { color: '#000000' } }
    },
    dataLabels: {
      //  enabled: true,
      enabled: this.isMobileView ? false : true,

      style: {
        fontSize: '10px',
        colors: ['#000000'],
        fontWeight: 'normal' // Remove bold styling by setting fontWeight to normal
      }
    },
    chart: {
      type: 'bar',
      height: 1000,
      stacked: true
    },
    plotOptions: { bar: { horizontal: false } },
    fill: { opacity: 1 },
    // colors: [
    //     '#80c7fd', '#008FFB', '#80f1cb', '#00E396', '#feb019',
    //     '#FF5733', '#FFBD33', '#C70039', '#900C3F', '#581845',
    //     '#2ECC71', '#28B463', '#239B56', '#1D8348', '#186A3B'
    // ],t
    colors: [
      '#007bff',
      '#28a745',
      '#ffc107', 
      '#dc3545', 
      '#ff5733',
      // Blue, red-orange, green, amber, crimson
      // '#6f42c1',
      // '#17a2b8',
      // '#fd7e14',
      // '#6610f2',
      // '#e83e8c', // Purple, teal, orange, violet, pink
      // '#20c997',
      // '#ff6347',
      // '#8b0000',
      // '#4169e1',
      // '#32cd32' // Aqua, tomato, dark red, royal blue, lime green
    ],
    legend: { position: isMobile ? 'top' : 'bottom', horizontalAlign: 'center' }
  };

  this.dashboardService.regionwiseGrowthOverPreviousYearMonthly(MonthlyToalOrdaringPayload).subscribe(
    (response) => {
      if (response && response.body) {
        this.spinner.hide();
// this.RegionWiseGrowthOverPreviousMonthForPreviousYear(MonthlyToalOrdaringPayload);
        // Prepare categories based on unique months
        const uniqueMonths = [...new Set(response.body.map((item: any) => item.month))];
        uniqueMonths.forEach((month: any) => {
          categories.push(monthNames[month - 1]);
        });

        // Function to prepare series data
        // const prepareSeriesData = (data: any[], metric: string, group: string) => {
        //   const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
        //   const metricLabels: { [key: string]: string } = {
        //     retailerGrowth: 'Retailer Growth',
        //     orderGrowth: 'Order Growth (K)',
        //     priceGrowth: 'Price Growth (Cr)'
        //   };

        //   return regions.map((region) => {
        //     const regionData = data.filter((item) => item.region === region);
        //     return {
        //       name: `${metricLabels[metric]} - ${region}`,
        //       group: group,
        //       data: regionData.map((item) => {
        //         if (metric === 'orderGrowth') return (item.orderGrowth / 1000).toFixed(2); // Adjust units
        //         if (metric === 'priceGrowth') return ((item.priceGrowth / 10000000).toFixed(2)); // Adjust units
        //         return item.retailerGrowth;
        //       })
        //     };
        //   });
        // };

        const prepareSeriesData = (data: any[], metric: string, group: string) => {
          const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];
          const metricLabels: { [key: string]: string } = {
            retailerGrowth: 'Retailer Growth',
            orderGrowth: 'Order Growth (K)',
            priceGrowth: 'Price Growth (Cr)'
          };
        
          return regions.map((region) => {
            const regionData = data.filter((item) => item.region.toUpperCase() === region.toUpperCase()); // Case-insensitive comparison
            return {
              name: `${metricLabels[metric]} - ${region}`,
              group: group,
              data: regionData.map((item) => {
                if (metric === 'orderGrowth') return (item.orderGrowth / 1000).toFixed(2); // Adjust units
                if (metric === 'priceGrowth') return ((item.priceGrowth / 10000000).toFixed(2)); // Adjust units
                
                return item.retailerGrowth;
              })
            };
          });
        };
        
             const selectedMoths = this.selectedMonths;

        const regions = ['EAST','NORTH', 'SOUTH 1' ,'SOUTH 2', 'WEST'];

        // Update chart options with dynamic series and categories
        this.RegionWiseGrowthOverPreviousMonthOptionsPrevYear = {
          ...this.RegionWiseGrowthOverPreviousMonthOptionsPrevYear,
          series: [
            // ...prepareSeriesData(response.body, 'retailerGrowth', 'retailers'),
            // ...prepareSeriesData(response.body, 'orderGrowth', 'qty'),
            ...prepareSeriesData(response.body, 'priceGrowth', 'value')
          ],
          xaxis: { ...this.RegionWiseGrowthOverPreviousMonthOptionsPrevYear.xaxis, categories },
          title: {
            text: response.body && response.body.length > 0 ? 'Region Wise Growth Over Previous Year' :'No Data Available',
            align: 'center'
            // style: { fontSize: '16px', fontWeight: 'bold', color: '#333' }
          },
          yaxis: [
            {
              title: {
                text: 'Price (Crs)',
                style: { 
                  fontFamily:'unset'
               },
                offsetX:20
              },
              labels: {
                formatter: (val: any) => '' + val
              },
              tickAmount: 10,
            }
          ],

        //   tooltip: {
        //     enabled: true,
        //     custom: function({ series, seriesIndex, dataPointIndex, w }) {
        //         // Find the region name based on seriesIndex
        //         const regionName = regions[seriesIndex];
        
        //         // Find the month corresponding to dataPointIndex (e.g., dataPointIndex 0 = month 4)
        //         const month = dataPointIndex + 4;
        
        //         // Filter the response array to find the matching record
        //         const record = response.body.find(
        //             (item:any) => item.region === regionName && item.month === month
        //         );
        
        //         // Build the tooltip content
        //         let tooltipContent = `
        //             <div style="padding: 8px; font-size: 12px; background: white; border: 1px solid #ddd;">
        //                 <strong>${regionName} Region</strong>
        //         `;
        
        //         if (record) {
        //             // Add data if record is found
        //             // tooltipContent += `
        //             //     <div>Retailers: ${record.totalRetailerCode}</div>
        //             //     <div>Qty (K): ${record.totalQTY/1000}</div>
        //             //     <div>Value (Cr): ${record.totalRevenue/10000000}</div>
        //             // `;


                    
        //             tooltipContent += `
        //     <div>RetailerGrowth: ${record.retailerGrowth}</div>
        //     <div>OrderGrowth (K): ${(record.orderGrowth / 1000).toFixed(2)}</div>
        //     <div>PriceGrowth (Cr): ${(record.priceGrowth / 10000000).toFixed(2)}</div>
        // `;
        
        //         } else {
        //             // Fallback content if no record is found
        //             tooltipContent += `<div>No data available</div>`;
        //         }
        
        //         tooltipContent += `</div>`;
        //         return tooltipContent;
        //     }
        // }

        tooltip: {
          enabled: true,
          custom: function({ series, seriesIndex, dataPointIndex, w }) {
              // Find the region name based on seriesIndex
              const regionName = regions[seriesIndex];
      
              // Find the month corresponding to dataPointIndex (e.g., dataPointIndex 0 = month 4)
           let month =  dataPointIndex + 4;
      
           if(selectedMoths &&  selectedMoths.length > 0 ){
            // Find the earliest month and year
            const ascendingOrderData = selectedMoths.sort((a:any, b:any) => {
             return a.id - b.id;
           });
           
           if(ascendingOrderData){
             const monthsData= [
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
      
          const getFiltedData = monthsData.filter(
            month => 
              month.id >= ascendingOrderData[0].id && 
              month.id <= ascendingOrderData[ascendingOrderData.length - 1].id
          );
        
            month = getFiltedData[dataPointIndex].id;
           }
            
           }
      
              // Filter the response array to find the matching record
              const record = response.body.find(
                  (item:any) => item.region === regionName && item.month === month
              );
      
      
              // Build the tooltip content
              let tooltipContent = `
                  <div style="padding: 8px; font-size: 12px; background: white; border: 1px solid #ddd;">
                      <strong>${regionName} Region</strong>
              `;
      
              if (record) {
                  // Add data if record is found
                  // tooltipContent += `
                  //     <div>Retailers: ${record.totalRetailerCode}</div>
                  //     <div>Qty (K): ${record.totalQTY/1000}</div>
                  //     <div>Value (Cr): ${record.totalRevenue/10000000}</div>
                  // `;
                  tooltipContent += `
                    <div>RetailerGrowth: ${record.retailerGrowth}</div>
            <div>OrderGrowth (K): ${(record.orderGrowth / 1000).toFixed(2)}</div>
            <div>PriceGrowth (Cr): ${(record.priceGrowth / 10000000).toFixed(2)}</div>`;
      
              } else {
                  // Fallback content if no record is found
                  tooltipContent += `<div>No data available</div>`;
              }
      
              tooltipContent += `</div>`;
              return tooltipContent;
          }
      }
          // tooltip: {
          //   shared: true,
          //   intersect: false,
          //   custom: ({ series, dataPointIndex, w }: any) => {
          //     // Retrieve the category (month or x-axis label)
          //     const category = w.globals.labels[dataPointIndex];
          
          //     // Define column headers
          //     const headers = ['Region', 'Retailer Growth', 'Order Growth', 'Price Growth'];
          
          //     // Construct rows for each region
          //     const tableRows = series[0].map((_: any, seriesIndex: number) => {
          //       const regionName = w.globals.seriesNames[seriesIndex];
          //       const retailerGrowth = series[0][dataPointIndex].toFixed(2); // Retailer Growth (series 0)
          //       const orderGrowth = (series[1][dataPointIndex] / 1000).toFixed(2); // Order Growth (series 1, divided by 1000)
          //       const priceGrowth = (series[2][dataPointIndex] * 1000).toFixed(2); // Price Growth (series 2, multiplied by 1000)
          
          //       return `
          //         <tr>
          //           <td style="padding: 5px; border: 1px solid #ccc;">${regionName}</td>
          //           <td style="padding: 5px; border: 1px solid #ccc;">${retailerGrowth}</td>
          //           <td style="padding: 5px; border: 1px solid #ccc;">${orderGrowth}</td>
          //           <td style="padding: 5px; border: 1px solid #ccc;">${priceGrowth}</td>
          //         </tr>
          //       `;
          //     }).join('');
          
          //     // Build the final tooltip content
          //     return `
          //       <div style="padding: 10px; border: 1px solid #ccc; background-color: #fff;">
          //         <strong>${category}</strong>
          //         <table style="width: 100%; border-collapse: collapse; margin-top: 10px;">
          //           <thead>
          //             <tr style="background-color: #f2f2f2;">
          //               ${headers.map(header => `<th style="padding: 5px; border: 1px solid #ccc; text-align: left;">${header}</th>`).join('')}
          //             </tr>
          //           </thead>
          //           <tbody>
          //             ${tableRows}
          //           </tbody>
          //         </table>
          //       </div>
          //     `;
          //   }
          // }
          
          
        };
      }
    },
    (error) => {
      console.error('Data loading error:', error);
    }
  );
};


// to get the ordercount for top 10 skus
public topSKUOrderedOverallpriceWiseFn = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.overallOrders = [];
  this.dashboardService.topSKUOrderedOverallpriceWise(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {
        // this.overallOrders = response.body.map((order:any) => {
        //   // Process productCode to remove slash if present
        //   let result = order.productCode.includes("/") ? order.productCode.replace("/", "") : order.productCode;
        //   // Create the image source URL based on the processed productCode
        //   const selectedImage = `assets/skuimages/${result}.jpg`;
        //   // Return the modified order with the new imageSrc field
        //   return {
        //     ...order,
        //     imageSrc: selectedImage
        //   };
        // });


       const overallOrders = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
         
        }));

        this.overAllResponseOrderLevel = overallOrders;
        this.overallOrders = overallOrders.slice(0, 5);
        this.selectImage(this.overallOrders[0],0);
        this.calculateOverallGrandTotalForTotalPrice();
        this.topSKUOrderedRegionSelectedpriceWiseFn(MonthlyTotalOrderingPayload);
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};

 // this is for getting the top sku qty.

 public topSKUOrderedRegionSelectedpriceWiseFn = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.regionTotals = [];
  this.dashboardService.topSKUOrderedRegionSelectedpriceWise(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {
        this.regionTotals = response.body;

        this.regionTotals = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
        }));
        this.overAllResponseRegionLevel = this.regionTotals;
        this.regionTotals = this.regionTotals.slice(0,5);

        if(this.regionTotals && this.regionTotals.length>0){
          this.selectImageforregion(this.regionTotals[0],0);
        }
        this.getRegionGrandTotalForTotalPrice();
        this.topSKUOrderedRSNameSelectedpriceWiseFn(MonthlyTotalOrderingPayload);
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};

// This is to get the rs names data.
public topSKUOrderedRSNameSelectedpriceWiseFn = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.rsTotals = [];
  this.dashboardService.topSKUOrderedRSNameSelectedpriceWise(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {

        
        this.rsTotals = response.body;
        this.rsTotals = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
        }));
this.overAllResponseRsOrderLevel = this.rsTotals;
this.rsTotals = this.rsTotals.slice(0,5);

        if(this.rsTotals && this.rsTotals.length>0){
          this.selectedImageSourceForRsFn(this.rsTotals[0],0);
        }
        this.getRSGrandTotalFortotalPrice();
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};


// Toggle dropdown visibility
toggleDropdownVisibilityForSelectionType() {
  this.toggleDropdownVisibility1('isDropdownOpenForSelectionType');
    if (this.isDropdownOpenForSelectionType) {
      this.filteredSelectionTypesList = this.filteredSelectionTypesList; // Reset filtered list on opening
    }
  // this.isDropdownOpenForSelectionType = !this.isDropdownOpenForSelectionType;
}

// Select a selection type
selectSelectionType(selectionType: string) {
  this.selectedSelectionType = selectionType;
  this.isDropdownOpenForSelectionType = false; // Close the dropdown after selection
}

// Clear selected selection type
clearSelectionType(event: MouseEvent) {
  event.stopPropagation(); // Prevent dropdown from closing immediately
  this.selectedSelectionType = null;
}

// Filter available selection types based on search input
filterAvailableSelectionTypes() {
  this.filteredSelectionTypesList = this.allSelectionTypesList.filter(type =>
    type.toLowerCase().includes(this.searchInputValueForSelectionType.toLowerCase())
  );
}




// this is to get the status of disable for search.
public getStatusOfDisableForAllFiltersExceptYearAndMonth(){

  if((this.selectedRegions && this.selectedRegions.length > 0) ||
   (this.selectedAbmNames && this.selectedAbmNames.length > 0) ||
    (this.selectedRetailerTypes && this.selectedRetailerTypes.length > 0) || 
    (this.selectedRSNames && this.selectedRSNames.length > 0)
     || (this.selectedBrands && this.selectedBrands.length > 0) || 
     (this.selectedStates && this.selectedStates.length > 0)||
     (this.selectedCities && this.selectedCities.length > 0)){
    return false;
  } else 
  {
    return true;
  }
}

 // this is for getting the top sku qty.

 public topRetailersOverallsum = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.overallOrders = [];
  this.dashboardService.topRetailersOverallsum(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {
        // this.overallOrders = response.body.map((order:any) => {
        //   // Process productCode to remove slash if present
        //   let result = order.productCode.includes("/") ? order.productCode.replace("/", "") : order.productCode;
        //   // Create the image source URL based on the processed productCode
        //   const selectedImage = `assets/skuimages/${result}.jpg`;
        //   // Return the modified order with the new imageSrc field
        //   return {
        //     ...order,
        //     imageSrc: selectedImage
        //   };
        // });


       const overallOrders = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
         
        }));

        this.overAllResponseOrderLevel = overallOrders;
        this.overallOrders = overallOrders.slice(0, 5);
        this.selectImage(this.overallOrders[0],0);
        this.calculateOverallGrandTotal();
        this.topRetailersRegionSelectedsum(MonthlyTotalOrderingPayload);
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};

// this is for getting the top sku qty.

public topRetailersOverallcount = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.overallOrders = [];
  this.dashboardService.topRetailersOverallcount(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {

       const overallOrders = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
         
        }));

        this.overAllResponseOrderLevel = overallOrders;
        this.overallOrders = overallOrders.slice(0, 5);
        this.selectImage(this.overallOrders[0],0);
        this.calculateOverallGrandTotal();
        this.topRetailersRegionSelectedcount(MonthlyTotalOrderingPayload);
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};


// this is for getting the top sku qty.

public topRetailersRegionSelectedsum = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.regionTotals = [];
  this.dashboardService.topRetailersRegionSelectedsum(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {
        this.regionTotals = response.body;

        this.regionTotals = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
        }));
        this.overAllResponseRegionLevel = this.regionTotals;
        this.regionTotals = this.regionTotals.slice(0,5);

        if(this.regionTotals && this.regionTotals.length>0){
          this.selectImageforregion(this.regionTotals[0],0);
        }
        this.getRegionGrandTotal();
        this.topRetailersRSNameSelectedsum(MonthlyTotalOrderingPayload);
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};

// this is for getting the top sku qty.

public topRetailersRegionSelectedcount = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.regionTotals = [];
  this.dashboardService.topRetailersRegionSelectedcount(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {
        this.regionTotals = response.body;

        this.regionTotals = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
        }));
        this.overAllResponseRegionLevel = this.regionTotals;
        this.regionTotals = this.regionTotals.slice(0,5);

        if(this.regionTotals && this.regionTotals.length>0){
          this.selectImageforregion(this.regionTotals[0],0);
        }
        this.getRegionGrandTotal();
        this.topRetailersRSNameSelectedcount(MonthlyTotalOrderingPayload);
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};





// This is to get the rs names data.
public topRetailersRSNameSelectedsum = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.rsTotals = [];
  this.dashboardService.topRetailersRSNameSelectedsum(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {

        
        this.rsTotals = response.body;
        this.rsTotals = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
        }));
this.overAllResponseRsOrderLevel = this.rsTotals;
this.rsTotals = this.rsTotals.slice(0,5);

        if(this.rsTotals && this.rsTotals.length>0){
          this.selectedImageSourceForRsFn(this.rsTotals[0],0);
        }
        this.getRSGrandTotal();
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};


// This is to get the rs names data.
public topRetailersRSNameSelectedcount = (MonthlyTotalOrderingPayload?: any) => {
  this.spinner.show();
  this.rsTotals = [];
  this.dashboardService.topRetailersRSNameSelectedcount(MonthlyTotalOrderingPayload).subscribe(
    async (response) => {
      if (response && response.body) {

        
        this.rsTotals = response.body;
        this.rsTotals = await Promise.all(response.body.map(async (order: any) => {
          // Process productCode to remove slash if present
          let result = order.productCode.includes("/") ? order.productCode.replace("/P", "") : order.productCode;
        
          // Create the image source URL based on the processed productCode
          const selectedImage = `assets/skuimages/${result}.jpg`;
        
          // Check if the image exists
          const imageExists = await this.dashboardService.checkImageExists(selectedImage);
        
          // Return the modified order with the new imageSrc and errorImg field
          return {
            ...order,
            imageSrc: selectedImage,
            errorImg: !imageExists // If image doesn't exist, set errorImg to true
          };
        }));
this.overAllResponseRsOrderLevel = this.rsTotals;
this.rsTotals = this.rsTotals.slice(0,5);

        if(this.rsTotals && this.rsTotals.length>0){
          this.selectedImageSourceForRsFn(this.rsTotals[0],0);
        }
        this.getRSGrandTotal();
      }
    },
    (error) => {
      console.error('Error fetching monthly order data:', error);
      this.spinner.hide();
    }
  );
};

}
