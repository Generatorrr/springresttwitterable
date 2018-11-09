<template>
  <div v-if="pageInfo" class="mt-3 pager-container">
    <ul class="pagination">
      <li class="page-item disabled">
        <a class="page-link" tabindex="-1">Pages</a>
      </li>
      <li v-for="index in pagerBody"
          v-bind:class="{ active: index === pageInfo.currentPage, disabled: index === -1 }"
          class="page-item">
        <a class="page-link" tabindex="-1"
          v-on:click="loadMessagePage(index)">{{index !== -1 ? index : '...'}}</a>
      </li>
    </ul>
  </div>
</template>

<script>
  import axios from 'axios'
  
  export default {
    computed: {
      pageInfo() {
        return this.$store.getters.getPageInfo;
      },
      filter() {
        return this.$store.getters.getFilter;
      }
    },
    mounted() {
      this.updatePager();
    },
    methods: {
      loadMessagePage(page) {
        if (page === -1) {
          return;
        }
        axios
          .get(`${location.origin}/message?page=${page}&size=1${this.filter ? `&filter=${this.filter}` : ''}`)
          .then(response => {
            this.$store.commit('setMessages', response.data.messages);
            this.$store.commit('setPageInfo', response.data.page);
            this.updatePager();
          })
          .catch(err => {
            console.log(err)
          });
      },
      updatePager() {
        const currentPage = this.pageInfo.currentPage;
        const head = currentPage > 4
          ? [1, -1]
          : [1, 2, 3];
        const tail = currentPage < this.pageInfo.total - 3
                ? [-1, this.pageInfo.total]
                : [this.pageInfo.total - 2, this.pageInfo.total - 1, this.pageInfo.total];
        const bodyBefore = currentPage + 1 > 4 && currentPage < this.pageInfo.total - 1
                ? [currentPage - 2, currentPage - 1]
                : [];
        const bodyAfter = currentPage + 1 > 2 && currentPage < this.pageInfo.total - 3
                ? [currentPage + 1, currentPage + 2]
                : [];
        debugger;
        let tempBody = head.concat(bodyBefore);
        if (currentPage > 3 && currentPage < this.pageInfo.total - 2) {
          tempBody.push(currentPage);
        }
        tempBody = tempBody.concat(bodyAfter, tail);
        this.pagerBody = tempBody;
      },
    },
    data() {
      return {
        pagerBody: null,
      }
    }
  }
  
</script>

<style lang="scss">
  .pager-container {
    .pagination {
      justify-content: center;
    }
  }
</style>