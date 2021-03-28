class Pagination {
  table = document.querySelector('.paginate');
  tableBody = this.table.querySelector('tbody');
  pagination = document.querySelector('.pagination');
  leftArrow = this.pagination.querySelector('li:first-of-type');
  rightArrow = this.pagination.querySelector('li:last-of-type');

  rows = [...this.tableBody.querySelectorAll('tr')];
  totalRows = this.rows.length;

  activePage = 1;
  rowsPerPage = 10;
  numPages = Math.ceil(this.totalRows / this.rowsPerPage);

  rowStart = (this.activePage - 1) * this.rowsPerPage;
  rowEnd = (this.activePage * this.rowsPerPage);

  visibleRows = this.rows.slice(this.rowStart, this.rowEnd);

  constructor() {
    this.displayPageNums();
    this.displayRows();
    this.addEventListeners();
  }

  displayRows() {
    this.rows.map((row, i) => {
      if(i + 1 <= this.rowStart || i + 1 > this.rowEnd)
        row.classList.add('hide')
      else
        row.classList.remove('hide');
    });
  }

  displayPageNums() {
    const pageNums = document.getElementsByClassName('pagination-num');
    
    while(pageNums.length > 0){
      pageNums[0].parentNode.removeChild(pageNums[0]);
    }

    Array(this.numPages)
      .fill()
      .forEach((_, i) => {
        const newLi = `<li data-page-num="${i+1}" class="waves-effect pagination-num ${this.activePage === (i + 1) && 'active'}"><a href="#">${i+1}</a></li>`
        this.rightArrow.insertAdjacentHTML('beforebegin', newLi);
      });
  }

  updateVisibleRows() {
    this.rowStart = (this.activePage - 1) * this.rowsPerPage;
    this.rowEnd = (this.activePage * this.rowsPerPage);
    this.visibleRows = this.rows.slice(this.rowStart, this.rowEnd);
    this.displayRows();
    this.displayPageNums();
  }

  nextPage() {
    ++this.activePage;
    this.updateVisibleRows();
  }

  prevPage() {
    --this.activePage;
    this.updateVisibleRows();
  }

  movePage(pageNum) {
    this.activePage = pageNum;
    this.updateVisibleRows();
  }

  handlePageChange(e) {
    e.preventDefault();
    
    const target = e.target.closest('li');

    if(target.classList.contains('prev-page') && this.activePage !== 1) {
      console.log(this.activePage);
      this.prevPage();
    } else if(target.classList.contains('next-page') && this.activePage !== this.numPages) {
      console.log(this.activePage);
      this.nextPage();
    } else if(target.classList.contains('pagination-num')) {
      const pageNum = parseInt(target.dataset.pageNum);
      this.movePage(pageNum);
    }
  }

  addEventListeners() {
    this.pagination.addEventListener('click', (e) => this.handlePageChange(e));
  }

}

new Pagination();