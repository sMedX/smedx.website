import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-presentation',
  templateUrl: './presentation.component.html',
  styleUrls: ['./presentation.component.css']
})
export class PresentationComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }


  downloadPresentation() {
    console.log('downolading presentation');
    window.location.href = '/api/storage/misc/Media.pptx';
  }

}
