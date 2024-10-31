import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { OperatorFunction, Subscription, first } from 'rxjs';
import { DashboardService } from '../dashboard.service';
// import { LOGIN_URL } from 'src/app/services/auth.constant';
// import { BuildInfoService } from 'src/app/services/build-info.service';
// import { HttpService } from 'src/app/services/http.service';
// import { LocalStorageService } from 'src/app/services/storage.service';

interface BuildInfo {
  version: string;
  buildDate: string;
  commitHash: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  minutes!: number;
  subscription!: Subscription;
  loginForm!: FormGroup;
  submitted = false;
  errorMessage = '';
  isLoginFailed = false;
  isLoggedIn = false;
  buildInfo: BuildInfo | undefined;
  public allowOnlyAfterClearCache: any = true;
  public cacheStatus: any = true;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private dashboardservice:DashboardService
  ) {
    // this.localStorage.clear();
  }

  async ngOnInit(): Promise<void> {
    
    // document.body.style.overflow = 'hidden';
    this.loginForm = this.formBuilder.group({
      userName: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });

  }

  updateAssets() {
    const cacheBuster = `?v=${this.formatTimestamp(Date.now())}`;

    // Update CSS files
    const links = document.getElementsByTagName('link');
    for (let i = 0; i < links.length; i++) {
      const link = links[i];
      if (link.getAttribute('rel') === 'stylesheet') {
        const href = link.getAttribute('href');
        if (href) {
          link.setAttribute('href', `${href.split('?')[0]}${cacheBuster}`);
        }
      }
    }

    // Update JavaScript files
    const scripts = document.getElementsByTagName('script');
    for (let i = 0; i < scripts.length; i++) {
      const script = scripts[i];
      const src = script.getAttribute('src');
      if (src) {
        script.setAttribute('src', `${src.split('?')[0]}${cacheBuster}`);
      }
    }
  }

  async clearAllCaches() {
    if ('caches' in window) {
      try {
        const cacheDetails = [];
        const cacheNames = await caches.keys();
        for (const cacheName of cacheNames) {
          const cache = await caches.open(cacheName);
          const requests = await cache.keys();
          const cacheEntries = [];
          for (const request of requests) {
            const response = await cache.match(request);
            cacheEntries.push({
              url: request.url,
              response: response ? response.clone() : null,
            });
          }
          cacheDetails.push({ name: cacheName, entries: cacheEntries });
          await caches.delete(cacheName);
        }
        // Store cache details in local storage
        const timestamp = this.formatTimestamp(Date.now());
        const data = { timestamp: timestamp, data: cacheDetails };
        localStorage.setItem('cachedetails', JSON.stringify(data));

        console.log(
          // 'cache details ' + this.localStorage.getItem('cachedetails')
        );
        console.log('All caches cleared and details stored in local storage.');
      } catch (error) {
        console.error('Error clearing caches:', error);
      }
    } else {
      console.warn('Cache API not supported in this browser.');
    }
  }

  formatTimestamp(timestamp: any) {
    const date = new Date(timestamp);
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${hours}:${minutes}:${seconds}`;
  }

  async onSubmit() {
    localStorage.setItem('initialLogin', '0');
    const nocatch = new Date().getTime();
    this.router.navigate(['/dashboard/charts'], {
      queryParams: { nocatch },
    });
    this.dashboardservice
      .login(this.loginForm.value.userName, this.loginForm.value.password)
      .pipe(first())
      .subscribe(
        async (data) => {
          console.log('LOG RES');
          const nocatch = new Date().getTime();
          this.router.navigate(['/dashboard/charts'], {
            queryParams: { nocatch },
          });

          /*   this.router.navigate(['/pages/' + page], {
            queryParams: { reload: new Date().getTime() },
          }); */
          //this.ngxService.stop();
        },
        (error) => {
          // this.ngxService.stop();
          this.errorMessage = error.error.message;
          this.isLoggedIn = false;
          this.isLoginFailed = true;
    //     }
        }
      );
    }
    
  
  showPassword() {
    var x: any = document.getElementById('password');
    if (x.type === 'password') {
      x.type = 'text';
    } else {
      x.type = 'password';
    }
  }
}
function pipe(arg0: OperatorFunction<unknown, unknown>) {
  throw new Error('Function not implemented.');
}
