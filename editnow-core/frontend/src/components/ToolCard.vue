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
                        :disabled="canUseActionTools"
                        class="btn btn-primary">
                    {{ actionTool.name }}
                    <tool-card-parameters-modal v-if="actionTool.parameterInfoDtos && actionTool.parameterInfoDtos.length > 0"
                                                :modal-id="actionTool.name"
                                                :modal-title="actionTool.name + ' parameters input modal' "
                                                :parameter-info-dtos="actionTool.parameterInfoDtos"/>
                </button>
            </b-card-body>
        </b-collapse>
    </b-card>
</template>

<script>
  import constVars from '../mixins/const-variables';
  import ToolCardParametersModal from "./ToolCardParametersModal";

  export default {
    name: "ToolCard",
    components: {ToolCardParametersModal},
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
        let actionRequest = {
          name: actionTool.name
        };
        console.log(actionTool.name, actionTool);
        if (actionTool.parameterInfoDtos && actionTool.parameterInfoDtos.length > 0) {
          this.getInputParameters(actionTool);
        } else {
          this.$eventBus.$emit(constVars.EVENT_ACTION_TOOL_CLICK, actionTool); //TODO przemapowany obiekt actionTool?
        }
      },
      getInputParameters(actionTool) {
        console.log('show', actionTool);
        this.$bvModal.show(actionTool.name);
      }
    },
    created() {
      const context = this;
      this.$eventBus.$on(constVars.EVENT_IS_IMAGE, (isImage) => {
        context.canUseActionTools = isImage;
      })
    }
  }
</script>

<style scoped>

</style>