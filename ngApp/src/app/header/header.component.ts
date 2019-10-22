import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {

  isCollapsed: boolean;

  public rootMenu: MenuItem = {
    path: '/main', title: '', selected: false
  };

  public menuItems: MenuItem[] = [
    { path: '/about', title: 'О нас', selected: false },
    { path: '/newsblog', title: 'Новости', selected: false },
    //  { path:'/cases', title:'Кейсы', selected:false},
    { path: '/product', title: 'Продукт', selected: false },
    { path: '/competencies', title: 'Наши Компетенции', selected: false },
    { path: '/contacts', title: 'Контакты', selected: false },
  ];


  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.isCollapsed = true;

    router.events.subscribe((val) => {
      // see also ;
      // console.log(val instanceof NavigationEnd) ;
      if (val instanceof NavigationEnd) {
        // console.log(val.url);
        this.selectPath(val.url);
      }
    });
    //  const id: Observable<string> = route.params.map(p => p.id);
    //  const url: Observable<string> = route.url.map(segments => segments.join(''));
    //  //  route.data includes both `data` and `resolve`
    //  const user = route.data.map(d => d.user);
  }


  ngOnInit() {
    //  let id = this.route.snapshot.paramMap.get('id');

    //  this.hero$ = this.service.getHero(id);
    // console.log(this.router.url);
    // console.log(this.router.routerState);
    // console.log(this.route.snapshot);
    const viewport = this.getViewport();
    if (viewport.width <= 600) {
      this.menuItems.splice(3, 0, { path: '/partners', title: 'Партнёры', selected: false });
      this.menuItems.join();
    }
  }

  selectMenu(menuItem: MenuItem) {
    // alert(menuItem);
    this.isCollapsed = true;
    const viewport = this.getViewport();
    if (viewport.width <= 600) {
      // document.getElementById('ham-menu').style.display = 'none';
      this.isCollapsed = false;
      this.toggle();
    }
    this.menuItems.forEach(element => {
      element.selected = true;
    });
    menuItem.selected = true;
    this.router.navigate([menuItem.path]);
  }

  selectPath(routeUrl: string) {
    this.isCollapsed = true;
    const viewport = this.getViewport();
    if (viewport.width <= 600) {
      // document.getElementById('ham-menu').style.display = 'none';
      this.isCollapsed = false;
      this.toggle();
    }
    this.menuItems.forEach(element => {
      //  console.log(element.path + ' == ' + routeUrl + ' is ' + (element.path === routeUrl));
      element.selected = element.path === routeUrl;
    });
  }

  checkRoute(): boolean {
    const viewport = this.getViewport();
    if (viewport.width <= 600) {
      return !(this.router.url === '/main') && this.isCollapsed;
    } else {
      return !(this.router.url === '/main');
    }
  }

  toggle() {
    this.isCollapsed = !this.isCollapsed;
    // console.log(this.isCollapsed ? 'Collapsing' : 'Opening');
    document.getElementById('ham-menu').style.display = this.isCollapsed ? 'none' : 'block';
    document.getElementById('body-div').style.display = this.isCollapsed ? 'block' : 'none';
    document.getElementById('contactForm').style.display = this.isCollapsed ? 'block' : 'none';
    document.getElementById('footer').style.display = this.isCollapsed ? 'block' : 'none';
    document.getElementById('ham-menu').style.height = window.screen.height * window.devicePixelRatio + 'px';
  }

  showRes() {
    // alert(window.screen.width * window.devicePixelRatio + ' x ' + window.screen.height * window.devicePixelRatio);
    // alert(window.screen.width + ' x ' + window.screen.height);
  }

  getViewport() {

    let viewPortWidth;
    let viewPortHeight;

    // the more standards compliant browsers (mozilla/netscape/opera/IE7) use window.innerWidth and window.innerHeight
    if (typeof window.innerWidth !== 'undefined') {
      viewPortWidth = window.innerWidth,
        viewPortHeight = window.innerHeight;
    } else if (typeof document.documentElement !== 'undefined'
      && typeof document.documentElement.clientWidth !==
      'undefined' && document.documentElement.clientWidth !== 0) {
      // IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)
      viewPortWidth = document.documentElement.clientWidth,
        viewPortHeight = document.documentElement.clientHeight;
    } else {
      // older versions of IE
      viewPortWidth = document.getElementsByTagName('body')[0].clientWidth,
        viewPortHeight = document.getElementsByTagName('body')[0].clientHeight;
    }
    return { width: viewPortWidth, height: viewPortHeight };
  }
}

export interface MenuItem {
  path: String;
  title: String;
  selected: Boolean;
}
