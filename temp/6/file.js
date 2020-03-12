function assert(condition){
    if(!condition){
        return false;
    }
    return true;
}function convertToF(celsius) {
  var fahrenheit = celsius * 9/5 + 32;

  return fahrenheit;
}console.log(assert(typeof convertToF(0) === 'number'));console.log(assert(convertToF(-30) === -22));console.log(assert(convertToF(-10) === 14));console.log(assert(convertToF(0) === 32));console.log(assert(convertToF(20) === 68));