// Angular Import
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {
  // public props
  windowWidth = window.innerWidth;
  @Output() NavMobCollapse = new EventEmitter();

  // public method
  navMobCollapse() {
    if (this.windowWidth < 992) {
      this.NavMobCollapse.emit();
    }
  }

  setElementValues(): void {
    const interval = setInterval(() => {
      const element = document.getElementById(
        'mobile-collapse'
      ) as HTMLInputElement;
      const openElement = document.getElementsByClassName('navbar-collapsed')[0];

      if (openElement) {
        // If the 'open' class element is found, stop the interval
        console.log('Sidebar is already open, stopping interval.');
        clearInterval(interval);
      } else if (element) {
        // If the 'open' class element is not found, perform the click and stop the interval
        element.click();
        console.log('Clicked to open the sidebar.');
        // clearInterval(interval);
      } else {
        console.error('One or more elements not found');
      }
    }, 100); // Check every 100ms until elements are available
  }


  ngAfterViewInit(): void {
    // this.cdr.detectChanges();
    // this.setElementValues();
  }
}
