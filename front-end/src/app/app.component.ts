import { Component } from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {LoaderComponent} from './components/loader/loader.component';
import {NgClass, NgIf} from '@angular/common';
import {FooterComponent} from './components/footer/footer.component';
import {HeaderComponent} from './components/header/header.component';
import {SidebarComponent} from './components/sidebar/sidebar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LoaderComponent, NgIf, FooterComponent, HeaderComponent, NgClass, SidebarComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Friend-finder';

  scrollToTop() {
    window.scrollTo({
      top: 0,
      behavior: 'smooth' });
  }

  isLoginPage = false;
  isDark = false;

  constructor(private router: Router) {
    this.router.events.subscribe(() => {
      this.isLoginPage = this.router.url === '/login' || this.router.url === '/signup' || this.router.url === '/login?loggedOut=true';
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
}
