import constVars from '../mixins/const-variables';

export default {
  emitActionRequestHistory(context, actionRequest) {
    const dataHistory = {
      name: actionRequest.actionType, type: 'INPUT'
    };
    if (actionRequest.parameters && actionRequest.parameters.length > 0) {
      dataHistory.parameters = [];
      actionRequest.parameters.forEach(parameter => dataHistory.parameters.push(parameter))
    }
    emitHistory(context, dataHistory);
  },
  emitResetHistory(context) {
    const dataHistory = {
      type: 'MESSAGE',
      message: 'Zresetowano historię wywołanych akcji',
      time: getCurrentTime()
    };
    context.$eventBus.$emit(constVars.EVENT_HISTORY_RESET, dataHistory);
  }
}

function emitHistory(context, dataHistory) {
  dataHistory.time = getCurrentTime();
  context.$eventBus.$emit(constVars.EVENT_HISTORY_CHANGE, dataHistory);
}

function getCurrentTime() {
  const date = new Date();
  return date.toLocaleTimeString();
}