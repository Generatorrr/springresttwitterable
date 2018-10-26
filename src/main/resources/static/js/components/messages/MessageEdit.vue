<template>
  <div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
      Message Editor
    </a>
    <div class="collapse" v-bind:class="{ show: message }" id="collapseExample">
      <div class="form-group mt-3">
        <form ref="messageForm" method="post" action="/message" enctype="multipart/form-data" >
          <div class="form-group">
            <input class="form-control" v-bind:class="{ 'is-invalid': textError }"
                   v-model="message && message.text" type="text" name="text" placeholder="Input your message" />
            <div v-if="textError" class="invalid-feedback">{{textError}}</div>
          </div>
          <div class="form-group">
            <input class="form-control" v-bind:class="{ 'is-invalid': textError }" type="text" name="tag" placeholder="Input your tag"
                   v-model="message && message.tag" />
            <div v-if="tagError" class="invalid-feedback">{{tagError}}</div>
          </div>
          <div class="custom-file">
            <input v-on:change="message && message.file" id="customFile" type="file" name="file" />
            <label for="customFile" class="custom-file-label">Choose file</label>
          </div>
          <input type="hidden" name="id" :value="message && message.id" />
          <div class="form-group">
            <button class="btn btn-primary mt-3" v-on:click="postMessage">Save message</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  
 export default {
   // props: [ 'message' ],
   data() {
     return {
       textError: null,
       tagError: null,
       message: {
         text: null,
         tag: null,
         file: null
       }
     }
   },
   methods: {
     postMessage() {
       debugger;
       axios
         .post(location.origin + this.$route.path, this.message)
         .then(() => {
           debugger;
         })
         .catch(err => {
           console.log(err);
         });
     }
   }
 }
 
</script>

<style>

</style>