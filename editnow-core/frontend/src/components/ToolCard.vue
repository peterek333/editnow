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
                <button class="btn"><i class="fa fa-home mb-2"></i></button>
                <button class="btn"><i class="fa fa-home"></i></button>
                <button class="btn"><i class="fa fa-home"></i></button>
                <b-card-text>{{ accordionId }}</b-card-text>
            </b-card-body>
        </b-collapse>
    </b-card>
</template>

<script>
    export default {
        name: "ToolCard",
        data () {
            return {
                accordionId: this.accordion + '-' + this.accordionName
            }
        },
        props: {
            accordion: String,
            accordionName: {
                type: String,
                required: true
            },
            visible: {
                type: Boolean,
                default: function () {
                    return false
                }
            }
        }
        // props: [
        //     'accordion', 'accordionName', 'visible'
        // ]
    }
</script>

<style scoped>

</style>