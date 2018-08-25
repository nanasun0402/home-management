// http://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parser: 'babel-eslint',
  parserOptions: {
    sourceType: 'module'
  },
  env: {
    browser: true,
  },
  // https://github.com/feross/standard/blob/master/RULES.md#javascript-standard-style
  //extends: 'standard',
  // required to lint *.vue files
  plugins: [
    'html'
  ],
  // add your custom rules here
  'rules': {
    /*'key-spacing': 0,
    'operator-linebreak': 0,
    'semi': 0,
    'object-property-newline': 0,
    'eol-last': 0,
    'no-unneeded-ternary': 0,
    'no-new': 0,
    'one-var': 0,
    'quotes': 0,
    'indent': 0,
    'no-undef': 0,
    'no-trailing-spaces': 0,
    'no-unused-vars': 0,
    'space-before-function-paren': 0,
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0*/
  }
}