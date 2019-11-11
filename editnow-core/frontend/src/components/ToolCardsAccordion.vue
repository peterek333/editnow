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
            v-bind:visible="toolCard.visible"></tool-card>
    </div>
</template>

<script>
    import ToolCard from "./ToolCard";
    export default {
        name: "ToolCardsAccordion",
        components: {ToolCard},
        data () {
            const toolCardsAccordion = 'Narzedzia';
            return {
                toolCardsTitle: toolCardsAccordion,
                toolCards: [
                    { accordion: toolCardsAccordion, accordionName: 'Przetwarzanie wstepne', visible: false },
                    { accordion: toolCardsAccordion, accordionName: 'Segmentacja obrazu', visible: false },
                    { accordion: toolCardsAccordion, accordionName: 'Detekcja krawedzi', visible: false },
                    { accordion: toolCardsAccordion, accordionName: 'Operacje morfologiczne', visible: false },
                ]
            }
        }
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