// angular import
import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApexOptions } from 'ng-apexcharts'; // Use ApexOptions instead of ChartOptions

// project import
import { SharedModule } from 'src/app/theme/shared/shared.module';
import { ApexGrid, ApexMarkers, ApexTitleSubtitle, NgApexchartsModule } from 'ng-apexcharts';
import { ProductSaleComponent } from './product-sale/product-sale.component';

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
} from "ng-apexcharts";
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
  markers:ApexMarkers;
  grid:ApexGrid;
};


@Component({
  selector: 'app-dash-analytics',
  standalone: true,
  imports: [CommonModule, SharedModule, NgApexchartsModule, ProductSaleComponent],
  templateUrl: './dash-analytics.component.html',
  styleUrls: ['./dash-analytics.component.scss']
})
export default class DashAnalyticsComponent {
  chartOptionsline!: ApexOptions; // Change to ApexOptions

  // public chartOptions5: {
  //   series: ApexAxisChartSeries;
  //   chart: ApexChart;
  //   xaxis: ApexXAxis;
  //   yaxis: ApexYAxis;
  //   colors: string[];
  //   fill: ApexFill;
  //   plotOptions: ApexPlotOptions;
  //   dataLabels: ApexDataLabels;
  // };

  // public chartOptions5: {
  //   series: ApexAxisChartSeries;
  //   chart: ApexChart;
  //   xaxis: ApexXAxis;
  //   yaxis: ApexYAxis;
  //   colors: string[];
  //   fill: ApexFill;
  //   plotOptions: ApexPlotOptions;
  //   dataLabels: ApexDataLabels;
  //   title: ApexTitleSubtitle;
  //   subtitle: ApexTitleSubtitle;
  //   stroke: ApexStroke;  
  // };
  // public props
  @ViewChild('chart') chart!: ChartComponent;
  chartOptions!: Partial<ChartOptions>;
  // chartOptionsline!: Partial<ChartOptions>;
  chartOptions_1!: Partial<ChartOptions>;
  chartOptions_2!: Partial<ChartOptions>;
  chartOptions_3!: Partial<ChartOptions>;

  // constructor
  constructor(public dashboardService:DashboardService) {
    
    this.chartOptions = {
      chart: {
        type: "bar",
        height: 350,
        toolbar: {
          show: false // Optional: Hide toolbar if not needed
        }
      },
      // title: {
      //   text: "Growth over Previous Month", // Chart title
      //   align: 'center',
      //   style: {
      //     fontSize: '16px',
      //     fontWeight: 'bold',
      //     color: '#333'
      //   }
      // },
      series: [
        {
          name: "Number of Retailers",
          data: [120, 150, 130] // Example values for Number of Retailers
        },
        {
          name: "Quantity (k)",
          data: [30, -25, 35] // Example values for Quantity
        },
        {
          name: "Value",
          data: [100, -70, 80] // Example values for Value
        }
      ],
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: "50%",
          // endingShape: "rounded"
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        show: true,
        width: 2,
        colors: ["transparent"]
      },
      xaxis: {
        categories: [
          "Feb",
          "Mar",
          "Apr",
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
          text: " (Growth percentage)"
        },
        min: -100, // Set a minimum value for y-axis to accommodate negative values
      },
      fill: {
        opacity: 1
      },
      tooltip: {
        y: {
          formatter: function(val) {
            return "" + val + " ";
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
    
    
    this.chartOptionsline = {
      series: [
        {
          name: "Retailers",
          data: [120, 150, 130]  // Example data for Retailers over 3 months
        },
        {
          name: "Quantity (k)",
          data: [30, 25, 35]  // Example data for Quantity over 3 months
        },
        {
          name: "Value",
          data: [100, 70, 80]  // Example data for Value over 3 months (in thousands)
        }
      ],
      chart: {
        height: 350,
        type: "line"
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        width: 5,
        curve: "smooth",  // Smooth curve for the line chart
        dashArray: [0, 8, 5]  // Different dash arrays for each line
      },
      title: {
        text: "Growth over Previous Months",  // Updated title
        align: "left"
      },
      legend: {
        tooltipHoverFormatter: function(val, opts) {
          return (
            val +
            " - <strong>" +
            opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] +
            "</strong>"
          );
        }
      },
      markers: {
        size: 5,  // Size of markers on the line
        hover: {
          sizeOffset: 6
        }
      },
      xaxis: {
        labels: {
          trim: false
        },
        categories: [
          "Month 1",  // Replace with actual month names or dates
          "Month 2",
          "Month 3"
        ]
      },
      yaxis: [
        {
          title: {
            text: "Value ($ thousands)",  // Left y-axis title
            style: {
              color: "#000000"  // Change color as needed
            }
          },
          min: 0,  // Minimum value for left y-axis
          labels: {
            formatter: function(val) {
              return "$" + val;  // Format for value
            }
          },
          tickAmount: 4  // Adjust the number of ticks as necessary
        },
        {
          opposite: true,  // Make this y-axis on the opposite side
          title: {
            text: "Quantity (Nos)",  // Right y-axis title
            style: {
              color: "#000000"  // Change color as needed
            }
          },
          min: 0,  // Minimum value for right y-axis
          tickAmount: 4  // Adjust the number of ticks as necessary
        }
      ],
      tooltip: {
        shared: true,  // Show shared tooltip
        intersect: false,
        y: [
          {
            title: {
              formatter: function(val) {
                return val + " Retailers";
              }
            }
          },
          {
            title: {
              formatter: function(val) {
                return val + " (k)";
              }
            }
          },
          {
            title: {
              formatter: function(val) {
                return "$" + val;  // Format for value
              }
            }
          }
        ]
      },
      grid: {
        borderColor: "#f1f1f1"
      }
    };
    
    
  }

  
  // life cycle event
  ngOnInit() {
    this.MonthlyToalOrdaring();

  }

  cards = [
    {
      background: 'bg-c-blue',
      title: 'Orders Received By South1 Region',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: '486',
      no: '351'
    },
    {
      background: 'bg-c-green',
      title: 'Orders Received By South2 Region',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: '486',
      no: '351'
    },
    {
      background: 'bg-c-yellow',
      title: 'Orders Received By East Region',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: '486',
      no: '351'
    },
    {
      background: 'bg-c-red',
      title: 'Orders Received By North Region',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: '486',
      no: '351'
    },
    {
      background: 'bg-c-red',
      title: 'Orders Received By West Region',
      icon: 'icon-shopping-cart',
      text: 'Total qty',
      number: '486',
      no: '351' 
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


  
  public MonthlyToalOrdaring = (data?:any)=>{
    this.dashboardService.MonthlyToalOrdaring().subscribe(
      (response) => {
         console.log("this is for checking")
      },
      (error) => {
        // this.errorMessage = 'An error occurred during login';
        console.error('Login error:', error);
      }
    );
  }
}
