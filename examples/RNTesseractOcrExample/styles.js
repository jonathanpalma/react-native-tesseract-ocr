import { PixelRatio, StyleSheet } from 'react-native';

const styles = StyleSheet.create({
  button: {
    margin: 5,
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  title: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
    paddingLeft: 10,
    paddingRight: 10,
  },
  imageContainer: {
    borderColor: '#9B9B9B',
    borderWidth: 1 / PixelRatio.get(),
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 20
  },
  image: {
    width: 150,
    height: 150,
  },
  rounded: {
    borderRadius: 75,
  }
});

export default styles;
