import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MessageService } from 'src/app/core/services/message.service';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent implements OnInit {
  name: String;
  email: String;
  message: String;

  constructor(
    private messageService: MessageService,
  ) { }

  ngOnInit() {
  }

  submit(form: NgForm) {
    console.log(form);
    // console.log(form.value.name);
    // console.log(form.value.email);
    // console.log(form.value.message);
    this.messageService.sendMessage(form.value.requester, form.value.email, form.value.message);
  }

}
