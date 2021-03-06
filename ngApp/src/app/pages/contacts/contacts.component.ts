import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {

  /** @Internal */
  public form: any = {};

  constructor() { }

  ngOnInit() {
  }

  subscribe(form: any) {
    // console.log(form);
    let mapForm = document.createElement('form');
    mapForm.target = '_blank';
    mapForm.method = 'POST'; // or "post" if appropriate
    mapForm.action = 'http://goodnewssmedxcom.dmsubscribe.ru/';
    mapForm.id = 'mc-embedded-subscribe-form';
    mapForm.name = 'mc-embedded-subscribe-form';
    this.appendInput('list_id', '19889', mapForm);
    this.appendInput('no_conf', '', mapForm);
    this.appendInput('notify', '', mapForm);
    this.appendInput('email', form.email, mapForm);
    this.appendInput('merge_1', form.name, mapForm);
    document.body.appendChild(mapForm);
    mapForm.submit();

  }

  appendInput(name, value, form) {
    var mapInput = document.createElement('input');
    mapInput.type = 'hidden';
    mapInput.name = name;
    mapInput.setAttribute('value', value);
    form.appendChild(mapInput);
  }
}
