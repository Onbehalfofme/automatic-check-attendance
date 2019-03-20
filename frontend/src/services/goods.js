export const getAllGoods = () => {
    // By default does GET request
    return fetch('https://5c912657c6354a0014037729.mockapi.io/goods/', { 
        method: 'GET',
        
        // method: 'POST',
        // data: JSON.stringify(data)
    });

    // const instance = axios.create({
    //     baseURL: 'https://some-domain.com/api/',
    //     timeout: 1000,
    //     headers: {'X-Custom-Header': 'foobar'}
    //   });
    // instance.get('/user?ID=12345')
    // .then(function (response) {
    //     // handle success
    //     console.log(response);
    // })
    // .catch(function (error) {
    //     // handle error
    //     console.log(error);
    // })
    // .then(function () {
    //     // always executed
    // });

    // (axios)/instance.post('/user', {
    //     firstName: 'Fred',
    //     lastName: 'Flintstone'
    // })
    // .then(function (response) {
    //     console.log(response);
    // })
    // .catch(function (error) {
    //     console.log(error);
    // });

}


export const getAllGoodsAsync = async () => {
    // By default does GET request
    try {
        const goods = await fetch('https://5c912657c6354a0014037729.mockapi.io/goods/');
        console.log (goods);
        return goods;
    } catch(e) {
        console.log ('Handle some error', e);
    }

}


export const getAllGoodsExample = () => {
    // By default does GET request
    fetch('https://5c912657c6354a0014037729.mockapi.io/goods/').then(() => console.log ('Hello from promise'));
    console.log('Hi there!');

    // > Hi there
    // > Hello ..

    // Is the same as async function setTimeout

    setTimeout(() => console.log ('Waittt!!!'), 5000);
    console.log ('Lol im the first');
}


// Usage
// Component.vue:
// import { getAllGoods, getAllGoodsAsync } from '../<path_to_goods.js>/goods.js';
// export default {
//  ....
//  created() {
//      getAllGoods()
//          .then((result) => console.log (result))
//          .catch((error) => console.error(error));
//      const goods = getAllGoodsAsync();
//      
//      or
//  async created() {
//      const goods = await getAllGoods();
//      console.log (goods);
//  }