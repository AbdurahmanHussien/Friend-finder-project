import { Component } from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {LoaderComponent} from './components/loader/loader.component';
import { NgClass } from '@angular/common';
import {FooterComponent} from './components/footer/footer.component';
import {HeaderComponent} from './components/header/header.component';
import {AuthService} from './service/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LoaderComponent, FooterComponent, HeaderComponent, NgClass],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Friend-finder';


  isLoginPage = false;
  isDark = false;

  constructor(private router: Router) {
    this.checkLoginPage(this.router.url);

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.checkLoginPage(event.urlAfterRedirects);
      }
    });
  }
  ngOnInit(): void {
    const theme = localStorage.getItem('theme');
    if (theme === 'dark') {
      document.documentElement.classList.add('dark');
      this.isDark = true;
    }
  }

  toggleDarkMode() {
    this.isDark = document.documentElement.classList.toggle('dark');
    localStorage.setItem('theme', this.isDark ? 'dark' : 'light');
  }


  checkLoginPage(url: string) {
    this.isLoginPage = url.includes('/login') || url.includes('/signup');
  }

  scrollToTop() {
    window.scrollTo({
      top: 0,
      behavior: 'smooth' });
  }

}
