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

  constructor(private http: HttpClient) { }

  getData(): Observable<any> {
    return this.http.get<any>(environment.apiUrl + '/stationary/loginSubmit');
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
      userName: username,
      password,
      // browsersignature: new ClientJS().getFingerprint().toString(),
    };
    return this.http
      .post<any>(
        `${environment.apiUrl}/stationary/loginSubmit`,
        JSON.stringify(loginInfo),
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



  public MonthlyToalOrdaring(data?:any) {
    const MonthlyToalOrdaringPayload = {
      data: 'rrrr',
      data2:'dddddd'
      // browsersignature: new ClientJS().getFingerprint().toString(),
    };
    return this.http
      .post<any>(
        `${environment.apiUrl}/stationary/api/MonthlyToalOrdaring`,
        JSON.stringify(MonthlyToalOrdaringPayload),
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      );      
  }
}
