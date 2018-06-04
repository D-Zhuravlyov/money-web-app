<template>
  <div id="hello">
    <h1>Here you can see you payment history and make new transactions!</h1>

    <form @submit.prevent="submit">
      <input type="number" v-model="input.amount" placeholder="Amount of transaction"/>
      <br>
      <input type="radio" id="add" value="add" v-model="input.picked">
      <label for="add">Add money</label>
      <br>
      <input type="radio" id="withdraw" value="withdraw" v-model="input.picked">
      <label for="withdraw">Withdraw money</label>
      <br>
      <button type="submit">
        Submit
      </button>
    </form>
    <p id="error" v-if="errors && errors.length">{{ errorMessage }}</p>
    <br>


    <div>
      <h1>Previous transactions:</h1>
    </div>

    <table>
      <tr>
        <td>User Name</td>
        <td>Sum of the operation</td>
        <td>Remained balance after transaction</td>
        <td>Date when the transaction occurred</td>
        <td>Operation type</td>
      </tr>
      <tr v-for="trans of transactionHistory">
        <td>{{trans.user.name}}</td>
        <td>{{trans.operationSum}}</td>
        <td>{{trans.remainedBalance}}</td>
        <td>{{ trans.transactionDate.substring(0, 19).replace('T', ' ') }}</td>
        <td>{{trans.operationType}}</td>
      </tr>
    </table>


  </div>
</template>

<script>
  import {AXIOS} from '../http/http-common'

  export default {
    name: 'HelloWorld',
    data() {
      return {
        transactionHistory: [],
        input: {
          amount: '',
          picked: ''
        },
        response: '',
        postBody: '',
        errors: [],
        errorMessage: ''
      }
    },
    created() {
      AXIOS.get(`api/users/01/history`)
        .then(response => {
          this.transactionHistory = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    },

    methods: {
      submit() {
        AXIOS.post(`api/operations/` + this.input.picked + '?' +
          'userId=' + '01' + '&' +
          'amount=' + this.input.amount
        )
          .then(response => {
            this.errorMessage = '';
          })
          .catch(e => {
            this.errorMessage = e.response.data;
            this.errors.push(e)
          });
      }
    }

  }

</script>

<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }

  td {
    padding-left: 20px;
  }

  #error {
    color: crimson;
  }

  .withdraw {
    background-color: mistyrose;
  }

  input[type=number] {
    width: 30%;
    border: none;
    border-bottom: 2px solid black;
    padding: 12px 20px;
    margin: 8px 0;
  }

  label {
    padding: 12px 20px;
    margin: 8px 0;
  }

  button {
    width: 5%;
    height: 2em;
    margin: 12px 20px;
  }

</style>
