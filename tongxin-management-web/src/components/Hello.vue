<template>
    <div class="hello">
        <h2>{{ msg }}</h2>
    </div>
</template>

<script>
export default {
  name: "hello",
  created() {},
  data() {
    return {
      msg: "欢迎使创优家居管理平台"
    };
  }
};
class A {}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello {
  text-align: center;
  color: #868686;
}

h2 {
  font-weight: normal;
}
</style>
