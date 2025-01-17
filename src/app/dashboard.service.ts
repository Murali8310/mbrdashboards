import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = '/stationary/loginSubmit'; // Replace with your API URL
  // http://localhost:8080/stationary/loginSubmit

  constructor(private http: HttpClient) {
    // localStorage.setItem('selectedData', '1');
   }
public selectedData:any = '1';
  getData(): Observable<any> {
    return this.http.get<any>(environment.apiUrl + '/stationary/loginSubmit');
  }
  
  logout(): Observable<any> {
    return this.http.get<any>(environment.apiUrl + '/logout');
  }

  

  // GetMasterData(payload: any): Observable<any> {
  //   return this.http.get<any>(environment.apiUrl + '/GetMasterData');
  // }

  public  GetMasterData(MonthlyToalOrdaringPayload?:any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/GetMasterData`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }

  

  // login(login_id: string, password: string): Observable<any> {
  //   const headers = new HttpHeaders({
  //     'Content-Type': 'application/json'
  //   });

  //   // Create the payload, encoding the password as needed
  //   const payload = {
  //     login_id: login_id,
  //     password: btoa(password)  // Encoding password to Base64
  //   };

  //   return this.http.post<any>(environment.apiUrl+this.apiUrl, payload, { headers });
  // }

  login(username: string, password: string) {
    const loginInfo = {
      login_id: username,
      password,
      // browsersignature: new ClientJS().getFingerprint().toString(),
    };
    return this.http
      .post<any>(
        `${environment.apiUrl}/loginSubmit`,
        loginInfo,
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      )
      .pipe(
        map((user:any) => {
          // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
          //   console.log(user.headers.get('token'));
          //   console.log(user.body);
          if (user.headers.get('token')) {
            // this.storeToken(user.headers.get('token'));
          }
          localStorage.setItem('userData', JSON.stringify(user.body));
          // this.userSubject.next(user.body);
          return user.body;
        })
      );
  }



  public  MonthlyToalOrdaring(MonthlyToalOrdaringPayload?:any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/MonthlyTrend`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }

  public  RegionWiseMonthlyDistribution(MonthlyToalOrdaringPayload?:any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/RegionWiseMonthlyDistribution`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }

  public  RegionWiseMonthlyDistibutionOptionsFOrdBhFn(MonthlyToalOrdaringPayload?:any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/regionWiseMonthlyDistributionNoofOrders`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }

  public  RegionWiseMonthlyAvgPerOrderFn(MonthlyToalOrdaringPayload?:any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/regionWiseMonthlyAvgPerOrder`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }


  /**
   * This is for geting the region wise growth over data.
   * @param MonthlyToalOrdaringPayload 
   * @returns 
   */
  public  RegionWiseGrowthOverPreviousMonth(MonthlyToalOrdaringPayload?:any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/RegionWiseGrowthOverPreviousMonth`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }

  /**
   * This is for geting the region wise growth over data.
   * @param MonthlyToalOrdaringPayload 
   * @returns 
   */
  public  regionwiseGrowthOverPreviousYearMonthly(MonthlyToalOrdaringPayload?:any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/regionwiseGrowthOverPreviousYearMonthly`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }

  /**
   * This is for geting the region wise growth over data fro prev year.
   * @param MonthlyToalOrdaringPayload 
   * @returns 
   */
  public  RegionWiseGrowthOverPreviousMonthForPreviousYear(MonthlyToalOrdaringPayload?:any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/RegionWiseGrowthOverPreviousMonthForPrevYear`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }
  

  public  GrowthOverPreviousMonth(MonthlyToalOrdaringPayload?:any) {
    // const GrowthOverPreviousMonthPayload :any = {
    //   "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
    //     "startDate":20240401,       /// default case start date of financial year in integer format
    //     "endDate": 202401030,       ////  default case end date of financial year in integer format
    //     "brandList": "",       //// default casen ""
    //     "rsNameList": ""//// default casen ""
    //   };

    return this.http
      .post<any>(
        `${environment.apiUrl}/GrowthOverPreviousMonth`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      ); 
    }

    public  GrowthOverPreviousYear(MonthlyToalOrdaringPayload?:any) {
      // const GrowthOverPreviousMonthPayload :any = {
      //   "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
      //     "startDate":20240401,       /// default case start date of financial year in integer format
      //     "endDate": 202401030,       ////  default case end date of financial year in integer format
      //     "brandList": "",       //// default casen ""
      //     "rsNameList": ""//// default casen ""
      //   };
  
      return this.http
        .post<any>(
          `${environment.apiUrl}/growthOverPreviousYearMonthly`,
          JSON.stringify(MonthlyToalOrdaringPayload),
          {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
            observe: 'response',
          }
        ); 
      }

    
      

      
      
      public  dashBoardInitalData(data?:any) {
        // const GrowthOverPreviousMonthPayload :any = {
        //   // "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
        //     "startDate":20241101,       /// default case start date of financial year in integer format
        //     "endDate": 202401130,       ////  default case end date of financial year in integer format
        //     // "brandList": "",       //// default casen ""
        //     // "rsNameList": ""//// default casen ""
        //   };
    
        return this.http
          .post<any>(
            `${environment.apiUrl}/dashboardTiles`,
            JSON.stringify(data),
            {
              headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
              observe: 'response',
            }
          ); 
  } 

  public  dashboardGraph(data?:any) {
    const dashboardGraph :any = {
      // "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
        "startDate":20240401,       /// default case start date of financial year in integer format
        "endDate": 202401030,       ////  default case end date of financial year in integer format
        // "brandList": "",       //// default casen ""
        // "rsNameList": ""//// default casen ""
      };

    return this.http
      .post<any>(
        `${environment.apiUrl}/dashboardGraphs`,
        JSON.stringify(dashboardGraph),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      ); 
} 


// This is for getting the data for avg orders epr month.
public  chartOptionslineForOrdBh(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/monthlyOrdaringBehaviour`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}

// This is for getting the data for avg orders epr month.
public  percentageOfOrdersOfDayInMonthFn(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/percentageofOrdersbyDayoftheMonth`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}


// This is for getting the data for avg orders epr month.
public  percentageofOrdersbyWeekdayorWeekendFn(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/percentageofOrdersbyWeekdayorWeekend`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}



// This is for getting the data for avg orders epr month.
public  percentageofOrdersByWeekdayorWeekendRegionWiseFn(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/percentageofOrdersByWeekdayorWeekendRegionWise`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}


// This is for getting the data for avg orders epr month.
public  percentaageOfOrdersByHourOfTheDayOnWeekdayWeekendFn(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/percentaageOfOrdersByHourOfTheDayOnWeekdayWeekend`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}


// This is for getting the data for avg orders epr month.
public  topSKUOrderedOverall(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topSKUOrderedOverall`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}

// This is for getting the data for avg orders epr month.
public  topSKUOrderedOverallpriceWise(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topSKUOrderedOverallpriceWise`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}




// This is for getting the data for avg orders epr month.
public  topSKUOrderedRegionSelected(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topSKUOrderedRegionSelected`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}


// This is for getting the data for avg orders epr month.
public  topRetailersRegionSelectedsum(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topRetailersRegionSelectedsum`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}

// This is for getting the data for avg orders epr month.
public  topRetailersRegionSelectedcount(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topRetailersRegionSelectedcount`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}



// This is for getting the data for avg orders epr month.
public  topSKUOrderedRegionSelectedpriceWise(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topSKUOrderedRegionSelectedpriceWise`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}


// This is for getting the data for avg orders epr month.
public  topSKUOrderedRSNameSelected(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topSKUOrderedRSNameSelected`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}


// This is for getting the data for avg orders epr month.
public  topRetailersRSNameSelectedsum(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topRetailersRSNameSelectedsum`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}



// This is for getting the data for avg orders epr month.
public  topRetailersRSNameSelectedcount(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topRetailersRSNameSelectedcount`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}

// This is for getting the data for avg orders epr month.
public  topSKUOrderedRSNameSelectedpriceWise(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topSKUOrderedRSNameSelectedpriceWise`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}


// Method to check if the image exists
checkImageExists(imageUrl: string): Promise<boolean> {
  return this.http.get(imageUrl, { observe: 'response', responseType: 'blob' }).toPromise()
    .then((response:any) => response.status === 200)  // Image exists if status is 200
    .catch(() => false);  // If error occurs (e.g., 404), return false
}



// This is for getting the data for avg orders epr month.
public  topRetailersOverallsum(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topRetailersOverallsum`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}


// This is for getting the data for avg orders epr month.
public  topRetailersOverallcount(MonthlyToalOrdaringPayload?:any) {
  return this.http
    .post<any>(
      `${environment.apiUrl}/topRetailersOverallcount`,
      JSON.stringify(MonthlyToalOrdaringPayload),
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      }
    );      
}



  
}
