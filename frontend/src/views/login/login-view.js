import {PolymerElement} from "@polymer/polymer/polymer-element.js";
import "@polymer/iron-iconset-svg/iron-iconset-svg.js";
import "@polymer/iron-icon/iron-icon.js";
import "@polymer/iron-form/iron-form.js";
import "@polymer/paper-tabs/paper-tabs.js";
import "@polymer/paper-tabs/paper-tab.js";
import "@polymer/iron-pages/iron-pages.js";
import "@vaadin/vaadin-button/vaadin-button";
import "@vaadin/vaadin-text-field/vaadin-text-field";
import "@vaadin/vaadin-text-field/vaadin-password-field";
import "@vaadin/vaadin-ordered-layout/vaadin-vertical-layout";
import {html} from "@polymer/polymer/lib/utils/html-tag.js";

class LoginView extends PolymerElement {

static get properties() {

  return {
    selected: {
      type: Number,
      value: 0,
      notify: true,
      reflectToAttribute: true
    }
  }
}


  static get template() {
    return html`
      <style>
        :host {
          --color-facebook: #3e5b96;
          --color-twitter: #2aa3ef;
          --color-google: #db4e47;
          display: flex;
          width: 400px;
          margin: 0 auto;
          flex-direction: column;
          padding-top: 300px;
          --lumo-border-radius-m: 0.25em;
          --lumo-font-size-s: .875rem;
          --lumo-secondary-text-color: hsla(214, 42%, 18%, 0.72);
          --lumo-primary-text-color: hsl(214, 90%, 52%); 
        }
        
        iron-form {
          box-shadow: 0 0 10px rgba(0,0,0,0.5);
          padding: 15px;
          background: lightslategray;
        }
        
        .login-form-container .text-upper {
          text-transform: uppercase;
          text-align: center;
          overflow: hidden;
        }
        
        .social-button iron-icon {
          position: absolute;
          left: .5rem;
          top: .44rem;
       }
       
       iron-icon {
          display: -ms-inline-flexbox;
          display: -webkit-inline-flex;
          display: inline-flex;
          -ms-flex-align: center;
          -webkit-align-items: center;
          align-items: center;
          -ms-flex-pack: center;
          -webkit-justify-content: center;
          justify-content: center;
       }
       .registration-dialog__social-button {
          position: relative;
          width: 100%;
          margin-top: .3rem;
       }
      .social-button {
          font-family: 'Montserrat';
          text-transform: none;
          border-radius: 50px;
          font-weight: 500;
          margin-bottom: .5rem;
          padding: .4rem .5rem;
          display: inline-block;
          text-align: center;
      }
      .button--social-text {
         font-weight: 700;
         font-size: 15px;
      }
      button, input, optgroup, select, textarea {
        color: inherit;
        font: inherit;
        margin: 0;
      }
      .button {
        margin: 0;
        padding: 0;
        border: 0;
        background: transparent;
        font-family: inherit;
        font-size: 1em;
        cursor: pointer;
        outline: 0;
        font-weight: 700;
        line-height: 1.6;
        color: #36393d;
        font-size: 0.875rem;
        color: #00b4f0;
        margin-bottom: 8px;
        position: relative;
        display: inline-block;
        height: auto;
        text-decoration: none;
        margin-top: 1.5rem;
        font-weight: 700;
        text-decoration: none;
        text-transform: uppercase;
        letter-spacing: .1em;
        transition: 100ms;
      }
      .button--bordered, .button--filled, .button--white {
        font-family: 'Montserrat';
        margin-bottom: 0;
        margin: 0;
        border: 0;
        padding: .8rem 1.6rem;
        background: #00b4f0;
        border-radius: 1000px;
        color: #fff;
        font-weight: 700;
        text-transform: uppercase;
        margin-top: 1rem;
      }
      .login-form-container .button--twitter {
        background-color: var(--color-twitter);
        color: #fff;
      } 
      .login-form-container .button--google {
        background-color: var(--color-google);
        color: #fff;
      }
      .login-form-container .button--facebook {
        background-color: var(--color-facebook);
        color: #fff;
      }

      
      vaadin-text-field, vaadin-password-field {
        width: 100%;
      }
      [part="label"]::after {
        content: "•";
        transition: opacity 0.2s;
        opacity: 0;
        color: var(--lumo-primary-text-color);
        position: absolute;
        right: 0;
        width: 1em;
        text-align: center;
      }
      [part="label"] {
        align-self: flex-start;
        color: var(--lumo-secondary-text-color);
        font-weight: 500;
        font-size: var(--lumo-font-size-s);
        margin-left: calc(var(--lumo-border-radius-m) / 4);
        transition: color 0.2s;
        line-height: 1;
        padding-bottom: 0.5em;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        position: relative;
        max-width: 100%;
        box-sizing: border-box;
      }

      label {
        cursor: default;
      }
      .login-form-container .vc-input {
        width: 100%;
      }

      .plain-tabs {
        --paper-tabs-selection-bar-color: #34C65D;
        --paper-tabs: {
        font-family: 'Montserrat';
        color: #fff;
        font-size: 16px;
        font-weight: 700;
        /*--lumo-contrast-60pct: #aaa;*/
        /*--lumo-body-text-color: #fff;*/
        display: inline-block;
        max-width: 100%;
        width: auto 
        }
      }

      .or-with-lines {
        text-align: center;
        overflow: hidden;
      }
      .or-with-lines > span:before {
        right: 100%;
        margin-right: 20px;
      }
      
      .or-with-lines > span:after {
        left: 100%;
        margin-left: 20px;
      }

      .or-with-lines > span:before, 
      .or-with-lines > span:after {
        content: "";
        position: absolute;
        height: 1px;
        top: 50%;
        background-color: #fff;
        width: 200px;
      }
      .or-with-lines > span {
        display: inline-block;
        position: relative;
        font-size: 14px;
        font-weight: 700;
      }
      
      </style>
      <iron-iconset-svg name="inline" size="24">
        <svg>
          <defs>
            <g id="google" viewBox = "0 0 45 45">
              <path d="M22.5,1.08A21.42,21.42,0,1,0,43.92,22.5,21.42,21.42,0,0,0,22.5,1.08Zm8.16,30.48a11.4,11.4,0,0,1-7.92,2.9,12,12,0,0,1,0-23.93,11.5,11.5,0,0,1,8,3.11l-3.38,3.3a6.63,6.63,0,0,0-4.63-1.78,7.23,7.23,0,0,0-6.82,5,7.74,7.74,0,0,0-.39,2.37,7.37,7.37,0,0,0,.4,2.37h0a7.2,7.2,0,0,0,6.81,5,7.08,7.08,0,0,0,4.15-1.2,5.85,5.85,0,0,0,2.45-3.88H22.74V20.32H34a10.25,10.25,0,0,1,.25,2.45A11.7,11.7,0,0,1,30.66,31.57Z"/>
            </g>
            
            <g id="twitter" viewBox = "0 0 45 45">
              <path d = "M22.5,2.5c-11,0-20,9-20,20s9,20,20,20s20-9,20-20S33.5,2.5,22.5,2.5zM30.6,18.9c0,0.2,0,0.3,0,0.5c0,6.2-4.9,11.2-11.1,11.2c-2.2,0-4.3-0.6-6.1-1.8c2.1,0.2,4.2-0.3,5.8-1.6c-1.7,0-3.2-1.1-3.7-2.7c0.6,0.1,1.2,0.1,1.8-0.1c-1.8-0.4-3.2-2-3.2-3.9c0,0,0,0,0,0c0.5,0.3,1.2,0.5,1.8,0.5c-1.7-1.2-2.3-3.5-1.2-5.3c2,2.5,5,4,8.1,4.1c-0.5-2.1,0.8-4.2,3-4.7c1.4-0.3,2.8,0.1,3.8,1.1c0.9-0.2,1.7-0.5,2.5-0.9c-0.3,0.9-0.9,1.7-1.7,2.2c0.8-0.1,1.5-0.3,2.3-0.6C32,17.7,31.4,18.3,30.6,18.9z"
                fill = "currentColor">
              </path>
            </g>
            <g id="facebook" viewBox="0 0 45 45">
               <path d="M22.5,2.5c-11,0-20,9-20,20s9,20,20,20s20-9,20-20S33.5,2.5,22.5,2.5zM27.2,16.3h-3c-0.4,0-0.8,0.5-0.8,1.1v2.2h3.7v3.1h-3.7V32h-3.5v-9.3h-3.2v-3.1h3.2v-1.9c-0.2-2.5,1.7-4.6,4.2-4.7c0,0,0.1,0,0.1,0h3V16.3z" fill="currentColor">
               </path>
            </g>
          </defs>
        </svg>
      </iron-iconset-svg>

      <div class="dialog-header">
      <s-svg src="img/rednavis-logo.svg" />
      </div>
      
      <paper-tabs id="plain-tabs" class="plain-tabs" selected="{{selected}}">
        <paper-tab>Login</paper-tab>
        <paper-tab>Sign up</paper-tab>
      </paper-tabs>


<!--      <div class="content">-->
      <iron-pages selected="{{selected}}">
       <div>
             <iron-form class="login" id="form" allow-redirect>

      <div class="login-form-container">
                  <button class="registration-dialog__social-button social-button button--google">
         <iron-icon icon="inline:google" id="google-icon" class="button-icon" role="img" aria-label="Google"></iron-icon>
         <span class="button--social-text">Login with Google</span>
      </button>
      <button class="registration-dialog__social-button social-button button--facebook">
         <iron-icon icon="inline:facebook" id="facebook-icon" class="button-icon" role="img" aria-label="Facebook"></iron-icon>
         <span class="button--social-text">Login with Facebook</span>
      </button>
      <button class="registration-dialog__social-button social-button button--twitter">
         <iron-icon icon="inline:twitter" id="twitter-icon" class="button-icon" role="img" aria-label="Twitter"></iron-icon>
         <span class="button--social-text">Login with Twitter</span>
      </button>
      
      <p class="text-upper text-muted or-with-lines"><span>Or</span></p>
                <form method="post" action="login">
                    <vaadin-vertical-layout>
                        <vaadin-text-field id="username" name="email" autocomplete="email" class="vc-input" label="Username" autofocus required has-label tabindex="0"></vaadin-text-field>
                        <input type="text" part="value" slot="input" name="email" tabindex="0" aria-labelledby="vaadin-text-field-label-3 vaadin-text-field-input-3" required="" aria-required="" autocomplete="email">
                        <vaadin-password-field autocomplete="current-password" id="password" class="vc-input" type="password" label="Password" name="password" required="" has-label="" tabindex="0">
                        <input type="password" part="value" slot="input" name="password" tabindex="0" aria-labelledby="vaadin-password-field-label-4 vaadin-password-field-input-4" required="" aria-required="" autocomplete="current-password"></vaadin-password-field>
                        <vaadin-button class="button button--filled registration-dialog__button" on-click="login">
                            LOGIN
                        </vaadin-button>
                    </vaadin-vertical-layout>
                </form>
      </div>
                  </iron-form>
       </div>
       <div>
              <iron-form class="login" id="form" allow-redirect>

      <div class="login-form-container">
                  <button class="registration-dialog__social-button social-button button--google">
         <iron-icon icon="inline:google" id="google-icon" class="button-icon" role="img" aria-label="Google"></iron-icon>
         <span class="button--social-text">Sign up with Google</span>
      </button>
      <button class="registration-dialog__social-button social-button button--facebook">
         <iron-icon icon="inline:facebook" id="facebook-icon" class="button-icon" role="img" aria-label="Facebook"></iron-icon>
         <span class="button--social-text">Sign up with Facebook</span>
      </button>
      <button class="registration-dialog__social-button social-button button--twitter">
         <iron-icon icon="inline:twitter" id="twitter-icon" class="button-icon" role="img" aria-label="Twitter"></iron-icon>
         <span class="button--social-text">Sign up with Twitter</span>
      </button>
      
      <p class="text-upper text-muted or-with-lines"><span>Or</span></p>
<!--                <form method="post" action="signup">-->
<!--                    <vaadin-vertical-layout>-->
<!--                        <vaadin-text-field id="email" name="email" placeholder="Email" autofocus required></vaadin-text-field>-->
<!--                        <vaadin-password-field id="password" name="password" placeholder="Password" required></vaadin-password-field>-->
<!--                        <vaadin-button on-click="signup" theme="primary">-->
<!--                            SIGN UP-->
<!--                        </vaadin-button>-->
<!--                    </vaadin-vertical-layout>-->
<!--                </form>-->
                <form id="provideEmailForm">
                <vaadin-vertical-layout>
                <vaadin-text-field autocomplete="email" id="signup-email" class="vc-input" label="Email" error-message="Invalid Email Format" required="" name="email" tabindex="0" has-label="">
                  <input type="email" part="value" slot="input" name="email" tabindex="0" aria-labelledby="vaadin-text-field-label-5 vaadin-text-field-input-5" required="" aria-required="" pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9-]{2,63}$" autocomplete="email">
                </vaadin-text-field>
                <vaadin-password-field autocomplete="new-password" id="signup-password" class="vc-input" type="password" label="Password" minlength="8" error-message="Password must be at least 8 characters" name="password" required="" tabindex="0" has-label="">
                  <input type="password" part="value" slot="input" name="password" tabindex="0" aria-labelledby="vaadin-password-field-label-6 vaadin-password-field-input-6" required="" aria-required="" minlength="8" autocomplete="new-password">
                </vaadin-password-field>
                <button type="submit" class="button button--filled button--create-account">Sign up</button>
                </vaadin-vertical-layout>
                </form>
      </div>
                  </iron-form>
       </div>
      </iron-pages>
      </div>
      
<!--      <iron-form class="login" id="form" allow-redirect>-->

<!--      <div id="header">Vaadin Showcase</div>-->
<!--      <div id="content">Log In</div>-->

<!--      <div class="login-form-container">-->
<!--                  <button class="registration-dialog__social-button social-button button&#45;&#45;google">-->
<!--         <iron-icon icon="inline:google" id="google-icon" class="button-icon" role="img" aria-label="Google"></iron-icon>-->
<!--         <span class="button&#45;&#45;social-text">Login with Google</span>-->
<!--      </button>-->
<!--      <button class="registration-dialog__social-button social-button button&#45;&#45;facebook">-->
<!--         <iron-icon icon="inline:facebook" id="facebook-icon" class="button-icon" role="img" aria-label="Facebook"></iron-icon>-->
<!--         <span class="button&#45;&#45;social-text">Login with Facebook</span>-->
<!--      </button>-->
<!--      <button class="registration-dialog__social-button social-button button&#45;&#45;twitter">-->
<!--         <iron-icon icon="inline:twitter" id="twitter-icon" class="button-icon" role="img" aria-label="Twitter"></iron-icon>-->
<!--         <span class="button&#45;&#45;social-text">Login with Twitter</span>-->
<!--      </button>-->
<!--      -->
<!--      <p class="text-upper text-muted or-with-lines"><span>Or</span></p>-->
<!--                <form method="post" action="login">-->
<!--                    <vaadin-vertical-layout>-->
<!--                        <vaadin-text-field id="username" name="username" placeholder="Username" autofocus required></vaadin-text-field>-->
<!--                        <vaadin-password-field id="password" name="password" placeholder="Password" required></vaadin-password-field>-->
<!--                        <vaadin-button on-click="login" theme="primary">-->
<!--                            Login-->
<!--                        </vaadin-button>-->
<!--                    </vaadin-vertical-layout>-->
<!--                </form>-->
<!--      </div>-->
<!--                  </iron-form>-->

<!-- -->    `;
  }

  static get is() {
    return "login-view";
  }

  login() {
    if (!this.$.username.invalid && !this.$.password.invalid) {
      this.$.form.submit();
    }
  }

}

customElements.define(LoginView.is, LoginView);