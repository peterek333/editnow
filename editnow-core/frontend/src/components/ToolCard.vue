<template>
    <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
            <b-button block href="#"
                      variant="info"
                      v-b-toggle="accordionId">
                {{ accordionName }}
            </b-button>
        </b-card-header>
        <b-collapse v-bind:id="accordionId"
                    v-bind:visible="visible"
                    v-bind:accordion="accordion" role="tabpanel">
            <b-card-body>
                <b-card-text>{{ accordionId }}</b-card-text>
                <button v-for="actionTool in actionTools"
                        :key="actionTool.name"
                        v-on:click="emitActionTool(actionTool)"
                        :disabled="!canUseActionTools"
                        class="btn btn-primary">
                    {{ actionTool.name }}
                </button>
            </b-card-body>
        </b-collapse>
    </b-card>
</template>

<script>
  export default {
    name: "ToolCard",
    data() {
      return {
        accordionId: this.accordion + '-' + this.accordionName,
        canUseActionTools: false
      }
    },
    props: {
      accordion: String,
      accordionName: {
        type: String,
        required: true
      },
      actionTools: Array,
      visible: {
        type: Boolean,
        default: function () {
          return false
        }
      }
    },
    methods: {
      emitActionTool(actionTool) {
        this.$emit('action-tool-click-event', actionTool);
        this.$eventBus.$emit('action-tool-click-event-bus', actionTool);
      }
    },
    created() {
      const context = this;
      this.$eventBus.$on('is-image-event', (isImage) => {
        context.canUseActionTools = isImage;
      })
    }
  }
</script>

<style scoped>

</style>