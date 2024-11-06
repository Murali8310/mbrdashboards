// angular import
import { Component, ViewChild, NO_ERRORS_SCHEMA, AfterViewInit } from '@angular/core';
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
  public appendClass: any;

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

  availableAbmNames = [
    { id: 1, name: 'ABM1' },
    { id: 2, name: 'ABM2' },
    { id: 3, name: 'ABM3' },
    { id: 4, name: 'ABM4' },
    { id: 5, name: 'ABM5' }
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
  chartOptions!: Partial<ChartOptions>;
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

  // constructor
  constructor(
    public dashboardService: DashboardService,
    private spinner: NgxSpinnerService
  ) {
    this.chartOptions = {
      chart: {
        type: 'bar',
        height: 350,
        toolbar: {
          show: false // Optional: Hide toolbar if not needed
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

    // this.chartOptionsline = {
    //   series: [
    //     {
    //       name: "Retailers",
    //       data: [120, 150, 130]  // Example data for Retailers over 3 months
    //     },
    //     {
    //       name: "Quantity (k)",
    //       data: [30, 25, 35]  // Example data for Quantity over 3 months (all positive)
    //     },
    //     {
    //       name: "Value",
    //       data: [100, 70, 80]  // Example data for Value over 3 months (all positive)
    //     }
    //   ],
    //   chart: {
    //     height: 350,
    //     type: "line"
    //   },
    //   dataLabels: {
    //     enabled: false
    //   },
    //   stroke: {
    //     width: 5,
    //     curve: "smooth",  // Smooth curve for the line chart
    //     dashArray: [0, 8, 5]  // Different dash arrays for each line
    //   },
    //   // title: {
    //   //   text: "Growth over Previous Months",  // Updated title
    //   //   align: "left"
    //   // },
    //   legend: {
    //     tooltipHoverFormatter: function(val, opts) {
    //       return (
    //         val +
    //         " - <strong>" +
    //         opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] +
    //         "</strong>"
    //       );
    //     }
    //   },
    //   // markers: {
    //   //   size: 5,  // Size of markers on the line
    //   //   hover: {
    //   //     sizeOffset: 6
    //   //   }
    //   // },
    //   xaxis: {
    //     labels: {
    //       trim: false
    //     },
    //     categories: [
    //       "july",  // Replace with actual month names or dates
    //       "august",
    //       "september"
    //     ]
    //   },
    //   tooltip: {
    //     y: [
    //       {
    //         title: {
    //           formatter: function(val) {
    //             return val + " Retailers";
    //           }
    //         }
    //       },
    //       {
    //         title: {
    //           formatter: function(val) {
    //             return val + " (k)";
    //           }
    //         }
    //       },
    //       {
    //         title: {
    //           formatter: function(val) {
    //             return "$" + val;  // Format for value
    //           }
    //         }
    //       }
    //     ]
    //   },
    //   // grid: {
    //   //   borderColor: "#f1f1f1"
    //   // }
    // };

    // this.chartOptionsline = {
    //   series: [
    //     {
    //       name: "Retailers",
    //       data: [120, 150, 130]  // Example data for Retailers over 3 months
    //     },
    //     {
    //       name: "Quantity (k)",
    //       data: [30, 25, 35]  // Example data for Quantity over 3 months
    //     },
    //     {
    //       name: "Value",
    //       data: [100, 70, 80]  // Example data for Value over 3 months (in thousands)
    //     }
    //   ],
    //   chart: {
    //     height: 350,
    //     type: "line"
    //   },
    //   dataLabels: {
    //     enabled: true
    //   },
    //   stroke: {
    //     width: 5,
    //     curve: "smooth",  // Smooth curve for the line chart
    //     dashArray: [0, 8, 5]  // Different dash arrays for each line
    //   },
    //   title: {
    //     text: "Monthly Trend.",  // Updated title
    //     align: "center"
    //   },
    //   legend: {
    //     tooltipHoverFormatter: function(val, opts) {
    //       return (
    //         val +
    //         " - <strong>" +
    //         opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] +
    //         "</strong>"
    //       );
    //     }
    //   },
    //   markers: {
    //     size: 5,  // Size of markers on the line
    //     hover: {
    //       sizeOffset: 6
    //     }
    //   },
    //   xaxis: {
    //     labels: {
    //       trim: false
    //     },
    //     categories: [
    //       "Month 1",  // Replace with actual month names or dates
    //       "Month 2",
    //       "Month 3"
    //     ]
    //   },
    //   yaxis: [
    //     {
    //       title: {
    //         text: "Value ($ thousands)",  // Left y-axis title
    //         style: {
    //           color: "#000000"  // Change color as needed
    //         }
    //       },
    //       min: 0,  // Minimum value for left y-axis
    //       labels: {
    //         formatter: function(val) {
    //           return "$" + val;  // Format for value
    //         }
    //       },
    //       tickAmount: 4  // Adjust the number of ticks as necessary
    //     },
    //     {
    //       opposite: true,  // Make this y-axis on the opposite side
    //       title: {
    //         text: "Quantity (Nos)",  // Right y-axis title
    //         style: {
    //           color: "#000000"  // Change color as needed
    //         }
    //       },
    //       min: 0,  // Minimum value for right y-axis
    //       tickAmount: 4  // Adjust the number of ticks as necessary
    //     }
    //   ],
    //   tooltip: {
    //     shared: true,  // Show shared tooltip
    //     intersect: false,
    //     y: [
    //       {
    //         title: {
    //           formatter: function(val) {
    //             return val + " Retailers";
    //           }
    //         }
    //       },
    //       {
    //         title: {
    //           formatter: function(val) {
    //             return val + " (k)";
    //           }
    //         }
    //       },
    //       {
    //         title: {
    //           formatter: function(val) {
    //             return "$" + val;  // Format for value
    //           }
    //         }
    //       }
    //     ]
    //   },
    //   grid: {
    //     borderColor: "#f1f1f1"
    //   }
    // };
  }

  // life cycle event
  async ngOnInit() {
    this.spinner.show();
    await this.GetMasterData();
    await this.MonthlyToalOrdaring();
    this.spinner.hide();
    // Simulate a delay for demonstration (e.g., data fetching)
    // setTimeout(() => {
    //   // Hide the spinner after the delay
    //   this.spinner.hide();
    // }, 3000);
  }

  cards = [
    {
      background: 'bg-c-blue',
      title: 'East',
      icon: 'icon-shopping-cart',
      number: '486',
      text: 'Total qty',
      order: '345',
      qtyText: 'Value',
      qtyValue: '333',
      dealercount: '20',
      no: '351'
    },
    {
      background: 'bg-c-green',
      title: 'North',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      qtyValue: '333',
      qtyText: 'Value',
      number: '486',
      no: '351'
    },
    {
      background: 'bg-c-yellow',
      title: 'South1',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: '486',
      qtyValue: '333',
      qtyText: 'Value',
      no: '351'
    },
    {
      background: 'bg-c-red',
      title: 'South2',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: '486',
      no: '351',
      qtyValue: '333',
      qtyText: 'Value'
    },
    {
      background: 'bg-c-red',
      title: 'West',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: '486',
      no: '351',
      qtyValue: '333',
      qtyText: 'Value'
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

  public MonthlyToalOrdaring = (data?: any) => {
    this.spinner.show();
    this.dashboardService.MonthlyToalOrdaring().subscribe(
      (response) => {
        if (response && response.body) {
          this.spinner.hide();

          // Define month names for easy mapping
          const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

          // Prepare data for each series
          const retailersData = [];
          const quantityData = [];
          const valueData = [];
          const categories: string[] = []; // This will hold the month names

          response.body.forEach((item: any) => {
            // Push values to each series array
            retailersData.push(item.totalRetailerCode);
            quantityData.push(item.totalQTY);
            valueData.push(item.totalRevenue);

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
                data: response.body.map((item: any) => item.totalRetailerCode) // Extracts totalRetailerCode values
              },
              {
                name: 'Quantity (k)',
                data: response.body.map((item: any) => item.totalQTY) // Extracts totalQTY values
              },
              {
                name: 'Value',
                data: response.body.map((item: any) => item.totalRevenue) // Extracts totalRevenue values
              }
            ],
            chart: {
              height: 350,
              type: 'line'
            },
            dataLabels: {
              enabled: true
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
            legend: {
              tooltipHoverFormatter: function (val, opts) {
                return val + ' - <strong>' + opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] + '</strong>';
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
                  text: 'Value (L)', // Left y-axis title
                  style: {
                    color: '#000000' // Change color as needed
                  }
                },
                min: 0, // Minimum value for left y-axis
                labels: {
                  formatter: function (val) {
                    return '' + val; // Format for value
                  }
                },
                tickAmount: 4 // Adjust the number of ticks as necessary
              },
              {
                opposite: true, // Make this y-axis on the opposite side
                title: {
                  text: 'Quantity', // Right y-axis title
                  style: {
                    color: '#000000' // Change color as needed
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
                  // title: {
                  //   formatter: function (val) {
                  //     return val + '(k)';
                  //   }
                  // }
                },
                {
                  title: {
                    formatter: function (val) {
                      return '' + val; // Format for value
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
    this.isDropdownOpen = !this.isDropdownOpen;
    if (this.isDropdownOpen) {
      this.filteredRegionsList = this.availableRegions; // Reset filtered list on opening
    }
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
    this.isDropdownOpenForAbm = !this.isDropdownOpenForAbm;
    if (this.isDropdownOpenForAbm) {
      this.filteredAbmNamesList = this.availableAbmNames; // Reset filtered list on opening
    }
  }

  // Filter ABM names based on search input
  filterAvailableAbmNames() {
    this.filteredAbmNamesList = this.availableAbmNames.filter((abm) =>
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
    this.isDropdownOpenForRetailer = !this.isDropdownOpenForRetailer;
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
    this.isDropdownOpenForRS = !this.isDropdownOpenForRS;
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
    this.isDropdownOpenForBrand = !this.isDropdownOpenForBrand;
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
    this.dashboardService.GetMasterData().subscribe(
      (response) => {
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
      },
      (error) => {
        // this.errorMessage = 'An error occurred during login';
        console.error('Login error:', error);
      }
    );
  }
}
