<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Hello</router-link>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'app',
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
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;
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
