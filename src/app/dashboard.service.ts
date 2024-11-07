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

  GetMasterData(): Observable<any> {
    return this.http.get<any>(environment.apiUrl + '/GetMasterData');
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



  public  MonthlyToalOrdaring(data?:any) {
    // const MonthlyToalOrdaringPayload = {
    //   data: 'rrrr',
    //   data2:'dddddd'
    //   // browsersignature: new ClientJS().getFingerprint().toString(),
    // };

    // const MonthlyToalOrdaringPayload :any = {
    //   "regionList": "EAST, WEST",  /// default all regions 
    //     "startDate":20240601,       /// default case start date of financial year in integer format
    //     "endDate": 20240730,       ////  default case end date of financial year in integer format
    //     "brandList": "",       //// default casen ""
    //     "rsNameList": ""//// default casen ""
    //   };

    const MonthlyToalOrdaringPayload :any = {
      "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
        "startDate":20240401,       /// default case start date of financial year in integer format
        "endDate": 202401030,       ////  default case end date of financial year in integer format
        "brandList": "",       //// default casen ""
        "rsNameList": ""//// default casen ""
      };

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

  public  GrowthOverPreviousMonth(data?:any) {
    const GrowthOverPreviousMonthPayload :any = {
      "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
        "startDate":20240401,       /// default case start date of financial year in integer format
        "endDate": 202401030,       ////  default case end date of financial year in integer format
        "brandList": "",       //// default casen ""
        "rsNameList": ""//// default casen ""
      };

    return this.http
      .post<any>(
        `${environment.apiUrl}/GrowthOverPreviousMonth`,
        JSON.stringify(GrowthOverPreviousMonthPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      ); 
    }
      

      
      
      public  dashBoardInitalData(data?:any) {
        const GrowthOverPreviousMonthPayload :any = {
          "regionList": "EAST, WEST,NORTH,SOUTH 1,SOUTH 2",  /// default all regions 
            "startDate":20240401,       /// default case start date of financial year in integer format
            "endDate": 202401030,       ////  default case end date of financial year in integer format
            "brandList": "",       //// default casen ""
            "rsNameList": ""//// default casen ""
          };
    
        return this.http
          .post<any>(
            `${environment.apiUrl}/dashBoardInitalData`,
            JSON.stringify(GrowthOverPreviousMonthPayload),
            {
              headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
              observe: 'response',
            }
          ); 
  } 
}
