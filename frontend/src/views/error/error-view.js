import {PolymerElement} from "@polymer/polymer/polymer-element.js";
import {html} from "@polymer/polymer/lib/utils/html-tag.js";

class ErrorView extends PolymerElement {
  static get template() {
    return html`
    <style>
      :host {
        width: 100%;
        height: 100%;
      }

      .error-wrapper {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 100%;
      }
    </style>
    
    <div id="content" class="error-wrapper"></div>`;
  }

  static get is() {
    return "error-view";
  }
}

customElements.define(ErrorView.is, ErrorView);