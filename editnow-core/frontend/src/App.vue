<template>
  <div id="app">
    <b-container id="app-container">
      <b-row class="justify-content-center container-header" style="height: 10%;">
      </b-row>
      <b-row class="align-items-center justify-content-center" style="height: 75%;">
        <router-view></router-view>
      </b-row>
      <b-row class="justify-content-center align-items-end"  style="height: 15%;">
        <span class="m-3 font-italic">{{ appBuildInfo }}</span>
      </b-row>
    </b-container>
    <div id="tools-menu">
      <tool-cards-accordion id="tool-cards-accordion"></tool-cards-accordion>
    </div>
    <div id="console-menu">
      <console-menu
              console-menu-title="Historia zmian"
      ></console-menu>
    </div>
  </div>
</template>

<script>

import ToolCardsAccordion from "./components/ToolCardsAccordion";
import ConsoleMenu from "./components/ConsoleMenu";
import appInfoApi from './components/api/app-info-api.js';

export default {
  name: 'app',
  components: {ConsoleMenu, ToolCardsAccordion},
  data () {
    return {
      appBuildInfo: "local"
    }
  },
  methods: {
  },
  created() {
    appInfoApi.getAppBuildInfo()
            .then(response => {
              this.appBuildInfo = response.data;
            })
            .catch(error => {
              console.log(error)
            });
  },
  beforeCreate() {
    console.log("Hello!");
    this.$store.dispatch('loadUUIDFromCookie')
            .catch((state) => {
              console.log(state);
              this.$store.dispatch('generateUUID');
            })
  }
}
</script>

<style lang="scss">
  html, body, #app, #app-container, #app-row {
    height: 100%;
  }
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #tools-menu {
    position: fixed;
    top: 10%;
    left: -2px;
    height: 75%;
    width: 200px;
  }

  #tool-cards-accordion {
    overflow-y: auto;
    max-height: 100%;
  }

  #console-menu {
    position: fixed;
    top: 10%;
    right: -2px;
    height: 75%;
    width: 300px;
  }
</style>
