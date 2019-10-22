import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-proposal',
  templateUrl: './proposal.component.html',
  styleUrls: ['./proposal.component.css']
})
export class ProposalComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  download(fileName: String) {
    console.log('downolading ' + fileName);
    window.location.href = '/api/storage/misc/' + fileName;
  }
}
