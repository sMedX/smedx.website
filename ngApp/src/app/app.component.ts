import { Component } from '@angular/core';
import { Meta } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  title = 'ngApp';
  resized = false;

  constructor(private meta: Meta) {
    this.setViewPort();
  }

  onResize(event) {
    this.setViewPort();
  }

  setViewPort() {
    if (window.screen.width <= 600) {
      if (this.meta.getTag('viewport')) {
        this.meta.getTag('viewport').content = 'width=600, maximum-scale=1.0, initial-scale=1.0';
      } else {
        this.meta.addTag({ name: 'viewport', content: 'width=600, maximum-scale=1.0, initial-scale=1.0' });
      }
    } else {
      if (this.meta.getTag('viewport')) {
        this.meta.getTag('viewport').content = 'width=device-width, maximum-scale=1.0, initial-scale=1.0';
      } else {
        this.meta.addTag({ name: 'viewport', content: 'width=device-width, maximum-scale=1.0, initial-scale=1.0' });
      }
    }
  }

  onActivate(event) {
    let scrollToTop = window.setInterval(() => {
      let pos = window.pageYOffset;
      if (pos > 0) {
        window.scrollTo(100, pos - 20); // how far to scroll on each step
      } else {
        window.clearInterval(scrollToTop);
      }
    }, 7);
  }
}
