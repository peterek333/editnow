<template>
    <div role="tablist" class="toolCards">
        <h4 class="m-1">
            {{ toolCardsTitle }}
        </h4>
        <tool-card
            v-for="toolCard in toolCards"
            :key="toolCard.accordionName"
            v-bind:accordion="toolCard.accordion"
            v-bind:accordion-name="toolCard.accordionName"
            v-bind:action-tools="toolCard.actionTools"
            v-on:action-tool-click-event="handleClick"
            v-bind:visible="toolCard.visible"></tool-card>
    </div>
</template>

<script>
    import ToolCard from "./ToolCard";
    import configurationApi from './api/configuration-api';

    export default {
      name: "ToolCardsAccordion",
      components: {ToolCard},
      data () {
        const toolCardsAccordion = 'Narzędzia';
        return {
          toolCardsTitle: toolCardsAccordion,
          toolCards: []
        }
      },
      methods: {
        getActionToolsConfiguration() {
          const context = this;

          configurationApi.getActionTools()
            .then(response => {
              const actionToolsInCategory = response.data;
              console.log(actionToolsInCategory);

              actionToolsInCategory.forEach( (actionToolInCategory, index) => {
                const categoryName = actionToolInCategory.categoryName;
                context.toolCards.push({
                  accordion: context.toolCardsTitle,
                  accordionName: categoryName,
                  actionTools: actionToolInCategory.actionTools,
                  visible: index === 0 //first will be open
                });
              })

            })
            .catch(error => {
              console.log(error)
            })
        },
        handleClick($event) {
          console.log($event.name)
        }
      },
      created() {
        this.getActionToolsConfiguration();
      },
    }
</script>

<style scoped>
    .toolCards {
        background-color: #e9ecef;
        border-radius: 0.3rem;
        -webkit-box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
        -moz-box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
        box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
    }
</style>