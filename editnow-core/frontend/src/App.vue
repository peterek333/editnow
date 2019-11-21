<template>
  <div id="app">
    <b-container id="app-container">
      <b-row class="justify-content-center" style="height: 10%;">
        header
      </b-row>
      <b-row class="align-items-center justify-content-center" style="height: 75%;">
        <router-view></router-view>
      </b-row>
      <b-row class="justify-content-center" style="height: 15%;">
        footer
      </b-row>
    </b-container>
    <div id="tools-menu">
      <tool-cards-accordion id="tool-cards-accordion"></tool-cards-accordion>
    </div>
    <div id="console-menu">

    </div>
  </div>
</template>

<script>

import ToolCardsAccordion from "./components/ToolCardsAccordion";
export default {
  name: 'app',
  components: {ToolCardsAccordion},
  data () {
    return {
    }
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
  #app-header {
    height: 15%;
  }
  #app-body {
    height: 70%;
  }
  #app-footer {
    height: 15%;
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
    left: 2px;
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
    right: 2px;
    height: 75%;
    width: 300px;

    border: 1px solid black;
  }
</style>
