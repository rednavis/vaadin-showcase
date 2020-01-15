import {PolymerElement} from "@polymer/polymer/polymer-element.js";
import {html} from "@polymer/polymer/lib/utils/html-tag.js";

class LoginView extends PolymerElement {
  static get template() {
    return html`
    <div id="header">Login View</div>
    <div id="content">I'm the future Login Form</div>
    <hr>
    <div id="footer">(c)RedNavis</div>`;
  }

  static get is() {
    return "login-view";
  }
}

customElements.define(LoginView.is, LoginView);