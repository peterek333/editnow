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
        const toolCardsAccordion = 'Narzedzia';
        return {
          toolCardsTitle: toolCardsAccordion,
          toolCards: [
            // { accordion: toolCardsAccordion, accordionName: 'Przetwarzanie wstepne', visible: false },
            // { accordion: toolCardsAccordion, accordionName: 'Segmentacja obrazu', visible: false },
            // { accordion: toolCardsAccordion, accordionName: 'Detekcja krawedzi', visible: false },
            // { accordion: toolCardsAccordion, accordionName: 'Operacje morfologiczne', visible: false },
          ]
        }
      },
      methods: {
        getActionToolsConfiguration() {
          const context = this;

          configurationApi.getActionTools()
            .then(response => {
              const actionToolsInCategory = response.data;
              console.log(actionToolsInCategory);

              actionToolsInCategory.forEach(actionToolInCategory => {
                const categoryName = actionToolInCategory.categoryName;
                context.toolCards.push({
                  accordion: context.toolCardsTitle,
                  accordionName: categoryName,
                  actionTools: actionToolInCategory.actionTools,
                  visible: false
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
        this.$eventBus.$on('action-tool-click-event-bus', (data) => {
          console.log('from event bus', data.name, data);
        });
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