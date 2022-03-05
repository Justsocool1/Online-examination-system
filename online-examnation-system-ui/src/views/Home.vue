<template>
  <div class="home">
    <home-page></home-page>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
import Demo from '@/components/Demo.vue'
import HomePage from '@/components/HomePage.vue'

export default {
  name: 'Home',
  components: {
    Demo,
    HomePage
  }
}
</script>
