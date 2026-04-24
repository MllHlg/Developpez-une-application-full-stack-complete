import { Component, OnInit } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.html',
    styleUrls: ['./home.scss'],
    imports: [MatButton, RouterModule]
})
export class HomeComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

}
