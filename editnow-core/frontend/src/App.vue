<template>
  <div id="app">
    <b-container id="app-container">
<!--      <b-row class="align-items-center" id="app-row">-->
<!--        <b-col id="app-header">-->
<!--          header-->
<!--        </b-col>-->
<!--        <b-col id="app-body">-->
<!--          body-->
<!--        </b-col>-->
<!--        <b-col id="app-footer">-->
<!--          footer-->
<!--        </b-col>-->
<!--      </b-row>-->
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

<!--      <div role="tablist">-->
<!--        <tool-card accordion="my-accordion" accordion-name="Narzedzia" visible="true"></tool-card>-->


<!--        <b-card no-body class="mb-1">-->
<!--          <b-card-header header-tag="header" class="p-1" role="tab">-->
<!--            <b-button block href="#" v-b-toggle.accordion-1 variant="info">Accordion 1</b-button>-->
<!--          </b-card-header>-->
<!--          <b-collapse id="accordion-1" visible accordion="my-accordion" role="tabpanel">-->
<!--            <b-card-body>-->
<!--              <b-card-text>I start opened because <code>visible</code> is <code>true</code></b-card-text>-->
<!--              <b-card-text>{{ text }}</b-card-text>-->
<!--            </b-card-body>-->
<!--          </b-collapse>-->
<!--        </b-card>-->

<!--        <b-card no-body class="mb-1">-->
<!--          <b-card-header header-tag="header" class="p-1" role="tab">-->
<!--            <b-button block href="#" v-b-toggle.accordion-2 variant="info">Accordion 2</b-button>-->
<!--          </b-card-header>-->
<!--          <b-collapse id="accordion-2" accordion="my-accordion" role="tabpanel">-->
<!--            <b-card-body>-->
<!--              <b-card-text>{{ text }}</b-card-text>-->
<!--            </b-card-body>-->
<!--          </b-collapse>-->
<!--        </b-card>-->

<!--        <b-card no-body class="mb-1">-->
<!--          <b-card-header header-tag="header" class="p-1" role="tab">-->
<!--            <b-button block href="#" v-b-toggle.accordion-3 variant="info">Accordion 3</b-button>-->
<!--          </b-card-header>-->
<!--          <b-collapse id="accordion-3" accordion="my-accordion" role="tabpanel">-->
<!--            <b-card-body>-->
<!--              <b-card-text>{{ text }}</b-card-text>-->
<!--            </b-card-body>-->
<!--          </b-collapse>-->
<!--        </b-card>-->
<!--      </div>-->

    </div>
<!--    <div id="nav">-->
<!--      <router-link to="/">Hello</router-link>-->
<!--    </div>-->
<!--    <router-view></router-view>-->
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

  #nav {
    padding: 30px;
    a {
      font-weight: bold;
      color: #2c3e50;
      &.router-link-exact-active {
       color: #42b983;
      }
  }
}
</style>
