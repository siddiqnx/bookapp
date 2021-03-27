document.addEventListener('DOMContentLoaded', function() {
  const tabEl = document.querySelector('.tabs');
  const bookTable = document.querySelector('#books-table');
  const selectEls = document.querySelectorAll('select');
  const summaryModalEl = document.querySelector('#summary-modal');
  const genreSelect = document.querySelector('.genre-select');
  const genreCustom = document.querySelector('.genre-custom-input');
  const genreCustomInput = genreCustom?.querySelector('#genre-name');
  const collapsibleEls = document.querySelectorAll('.collapsible');
  const addToCollectionBtn = document.querySelector('.add-collection-btn');
  const collectionSelect = document.querySelector('.collection-select');
  const addCollectionContainer = document.querySelector('.add-collection-container');
  const addCollectionForm = document.querySelector('#add-collection');

  M.Tabs.init(tabEl, {duration: 300});
  M.Modal.init(summaryModalEl);
  M.FormSelect.init(selectEls);
  M.Collapsible.init(collapsibleEls);

  const summaryModal = M.Modal.getInstance(summaryModalEl);

  bookTable.addEventListener('click', (e) => {
    if(e.target.classList.contains('book-checkbox')) {

      const isAtleastOneChecked = [...addCollectionForm.selected_books].some(x => x.checked);
      if(isAtleastOneChecked) {
        addCollectionContainer.classList.remove('hide');
      } else {
        addCollectionContainer.classList.add('hide');
      }
      return;
    }
    const target = e.target.closest('tr');
    const bookTitle = target.querySelector('.title').textContent;
    const bookSummary = target.querySelector('.summary').textContent;

    const modalHeaderEl = summaryModalEl.querySelector('.modal-title');
    const modalContentEl = summaryModalEl.querySelector('.modal-summary');

    modalHeaderEl.innerText = 'Summary for ' + bookTitle;
    modalContentEl.innerText = bookSummary;

    summaryModal.open();
  });
  
  const noPropagationLinks = [...document.querySelectorAll(".no-propagation")];
  
  noPropagationLinks.forEach(link => link.addEventListener("click", (e) => {
    e.stopPropagation();
  }));

  genreSelect?.addEventListener('change', (e) => {
    if(e.target.value === '0') {
      genreCustom.classList.remove('hide');
      genreCustomInput.value ='';
    } else {
      genreCustom.classList.add('hide');
      genreCustomInput.value = genreSelect.options[genreSelect.selectedIndex].text;
    }
  });

  collectionSelect.addEventListener('change', (e) => {
    if(e.target.value === '0') {
      collectionCustom.classList.remove('hide');
      collectionCustomInput.value ='';
    } else {
      collectionCustom.classList.add('hide');
      collectionCustomInput.value = collectionSelect.options[genreSelect.selectedIndex].text;
    }
  });
  
  addToCollectionBtn.addEventListener('click', (e) => {
    addToCollectionModal.open();
  });

});