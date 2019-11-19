<template>
    <b-modal v-bind:id="modalId" :title="modalTitle">
        <div v-for="parameterInfo in parameterInfoDtos"
             :key="parameterInfo.name">
            {{ parameterInfo.name + ': '}}
            <b-form-input v-if="parameterInfo.parameterType === 'INT'"
                          v-model="parameterInfo.value"></b-form-input>
        </div>
    </b-modal>
</template>

<script>
  export default {
    name: "ToolCardParametersModal",
    props: [
      'modalId', 'modalTitle', 'parameterInfoDtos'
    ],
    data() {
      return {
        value: null
      }
    },
    created() {
      console.log('created', this.modalId);
    }
  }
</script>

<style scoped>

</style>