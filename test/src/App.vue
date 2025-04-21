<template>
  <div class="login-container">
    <img 
      alt="Vue logo" 
      src="./assets/logo.png" 
      class="vue-logo"
    >
    <HelloWorld class="login-box"/>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    HelloWorld,

  }
}
</script>

<style>
#app {
  margin: 0;
  height: 100vh;
}

.login-container {
  position: relative;
  width: 100%;
  height: 100vh;
  background: url('https://zsb.hsu.edu.cn/_upload/article/images/aa/07/6f21d77e4e8caf2caa67a1b55df2/4c46b095-45aa-4474-bd74-1b28a03a5ce4.jpg') no-repeat center center fixed;
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
}

.vue-logo {
  position: absolute;
  top: 20px;
  left: 20px;
  height: 60px;
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0));
}

 .login-box {
  background: rgb(251, 251, 251, 0.3);
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0);
  /* width: 400px; */
} 
</style>